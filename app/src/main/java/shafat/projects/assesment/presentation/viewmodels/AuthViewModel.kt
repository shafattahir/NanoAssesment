package shafat.projects.assesment.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import shafat.projects.assesment.datasource.constants.DataState
import shafat.projects.assesment.datasource.repository.RepositoryAuth
import shafat.projects.assesment.presentation.states.AuthScreenState
import shafat.projects.assesment.utils.handleErrors
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val application: Application,
    private val repositoryAuth: RepositoryAuth
) : ViewModel(),
    LifecycleObserver {

    private val authScreenState: MutableLiveData<AuthScreenState> =
        MutableLiveData<AuthScreenState>(AuthScreenState.Idle)

    val authScreenStateObserver: LiveData<AuthScreenState>
        get() = authScreenState

    fun changeScreenState(newState: AuthScreenState) {
        when (newState) {
            is AuthScreenState.LoginInRequestSend -> {
                viewModelScope.launch {
                    repositoryAuth.loginUser(
                        newState.username ?: "",
                        newState.password ?: ""
                    ).onEach {
                        when (it) {
                            is DataState.Loading -> {
                                this@AuthViewModel.authScreenState.value =
                                    AuthScreenState.LoginInProgress
                            }

                            is DataState.Success -> {
                                this@AuthViewModel.authScreenState.value =
                                    AuthScreenState.LoginSuccessful(it.data ?: "")
                            }

                            is DataState.Error -> {
                                this@AuthViewModel.authScreenState.value = AuthScreenState.LoginFailed
                            }
                        }
                    }
                        .handleErrors(application.applicationContext)
                        .launchIn(viewModelScope)
                }
            }

            else -> {
                return
            }
        }
    }
}