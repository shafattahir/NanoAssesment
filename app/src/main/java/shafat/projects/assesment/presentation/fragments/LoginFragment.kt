package shafat.projects.assesment.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import shafat.projects.assesment.R
import shafat.projects.assesment.databinding.FragmentLoginBinding
import shafat.projects.assesment.presentation.states.AuthScreenState
import shafat.projects.assesment.presentation.viewmodels.AuthViewModel
import shafat.projects.assesment.utils.LoadingScreen
import shafat.projects.assesment.utils.disableButton
import shafat.projects.assesment.utils.enableButton
import shafat.projects.assesment.utils.getAuthToken
import shafat.projects.assesment.utils.isValidInput
import shafat.projects.assesment.utils.saveAuthToken
import shafat.projects.assesment.utils.showSnackBar

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by viewModels<AuthViewModel>()

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController

    private lateinit var mView: View
    private lateinit var loading: LoadingScreen

    private val emailAddress = MutableStateFlow(false)
    private val password = MutableStateFlow(false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        mView = binding.root
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        checkLoginStatus()
        attachClickListeners()
        setStateFlows()
        manageButtonState()
        attachViewModel()
    }

    private fun checkLoginStatus() {
        val token = requireContext().getAuthToken()
        if(token.isNotEmpty()){
            validateTokenAndFetchData(token)
        }
    }

    private fun init() {
        navController = requireView().findNavController()
        loading = LoadingScreen(requireContext())
    }

    private fun manageButtonState() {
        if (this::binding.isInitialized) {
            lifecycleScope.launch {
                formIsValid.collect {
                    if (it) binding.btnLogin.enableButton() else binding.btnLogin.disableButton()
                }
            }
        }
    }

    private fun setStateFlows() {
        if (this::binding.isInitialized) {
            with(binding) {
                etUserName.doOnTextChanged { text, _, _, _ ->
                    val passwordValid = isValidInput(text.toString())
                    emailAddress.value = passwordValid
                    if (passwordValid.not()) {
                        binding.tilEmail.error = resources.getString(R.string.email_required)
                        etUserName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                    }
                    else {
                        binding.tilEmail.error = null
                        etUserName.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, ResourcesCompat.getDrawable(resources,R.drawable.ic_tick,null),
                            null)
                    }

                }
                etPassword.doOnTextChanged { text, _, _, _ ->
                    val isEmailValid = isValidInput(text.toString())
                    password.value = isEmailValid
                    if (isEmailValid.not()) binding.tilPassword.error =
                        resources.getString(R.string.password_required)
                    else binding.tilPassword.error = null
                }
            }
        }
    }

    private fun attachClickListeners() {
        if (this::binding.isInitialized) {
            with(binding) {
                btnLogin.setOnClickListener { makeLoginRequest() }
            }
        }
    }

    private fun makeLoginRequest() {
        if (this::binding.isInitialized) {
            val email = binding.etUserName.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.changeScreenState(
                AuthScreenState.LoginInRequestSend(email, password)
            )
        }
    }

    private fun attachViewModel() {
        viewModel.authScreenStateObserver.observe(viewLifecycleOwner) {
            handleState(it)
        }
    }

    private fun handleState(state: AuthScreenState) {
        when (state) {
            AuthScreenState.Idle -> {

            }

            AuthScreenState.LoginInProgress -> {
                loading.displayLoading(false)
            }

            is AuthScreenState.LoginSuccessful -> {
                saveAuthToken(state.authToken?:"")
                loading.hideLoading()
                clearState()
            }

            is AuthScreenState.LoginFailed -> {
                loading.hideLoading()
                clearState()
                showSnackBar(mView, getString(R.string.login_error))
            }

            else -> {}
        }
    }

    private fun saveAuthToken(authToken: String) {
        requireContext().saveAuthToken(authToken)
        validateTokenAndFetchData(authToken)
    }

    private fun validateTokenAndFetchData(authToken: String) {
        if (authToken.isNotEmpty() && navController.currentDestination?.id == R.id.loginFragment){
            navController.navigate(R.id.action_loginFragment_to_allProductsFragment)
        }
    }


    private fun clearState() {
        viewModel.changeScreenState(
            AuthScreenState.Idle
        )
    }

    private val formIsValid = combine(
        emailAddress,
        password,
    ) { emailAddress, password ->
        emailAddress && password
    }

}