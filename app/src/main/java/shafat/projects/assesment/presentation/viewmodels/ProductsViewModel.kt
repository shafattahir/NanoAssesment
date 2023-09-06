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
import shafat.projects.assesment.datasource.repository.RepositoryProducts
import shafat.projects.assesment.presentation.states.ProductScreenState
import shafat.projects.assesment.utils.handleErrors
import javax.inject.Inject


@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val application: Application,
    private val repositoryProducts: RepositoryProducts
) : ViewModel(),
    LifecycleObserver {

    private val productScreenState: MutableLiveData<ProductScreenState> =
        MutableLiveData<ProductScreenState>(ProductScreenState.Idle)

    val productScreenStateObserver: LiveData<ProductScreenState>
        get() = productScreenState

    fun changeScreenState(newState: ProductScreenState) {
        when (newState) {
            is ProductScreenState.GetAllProductsRequestSend -> {
                viewModelScope.launch {
                    repositoryProducts.getAllProducts().onEach {
                        when (it) {
                            is DataState.Loading -> {
                                this@ProductsViewModel.productScreenState.value =
                                    ProductScreenState.GetAllProductsInProgress
                            }

                            is DataState.Success -> {
                                this@ProductsViewModel.productScreenState.value =
                                    ProductScreenState.GetAllProductsInSuccessful(it.data!!)
                            }

                            is DataState.Error -> {
                                this@ProductsViewModel.productScreenState.value =
                                    ProductScreenState.GetProductDetailsInFailed(it.message!!)
                            }
                        }
                    }
                        .handleErrors(application.applicationContext)
                        .launchIn(viewModelScope)
                }
            }

            is ProductScreenState.GetProductDetailsInRequestSend -> {
                viewModelScope.launch {
                    repositoryProducts.getProductDetails(newState.productID ?: 0).onEach {
                        when (it) {
                            is DataState.Loading -> {
                                this@ProductsViewModel.productScreenState.value =
                                    ProductScreenState.GetProductDetailsInProgress
                            }

                            is DataState.Success -> {
                                this@ProductsViewModel.productScreenState.value =
                                    ProductScreenState.GetProductDetailsInSuccessful(it.data!!)
                            }

                            is DataState.Error -> {
                                this@ProductsViewModel.productScreenState.value =
                                    ProductScreenState.GetProductDetailsInFailed(it.message!!)
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