package shafat.projects.assesment.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import shafat.projects.assesment.R
import shafat.projects.assesment.databinding.FragmentProductDetailsBinding
import shafat.projects.assesment.presentation.states.ProductScreenState
import shafat.projects.assesment.presentation.viewmodels.ProductsViewModel
import shafat.projects.assesment.utils.LoadingScreen
import shafat.projects.assesment.utils.showSnackBar

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private val viewModel by viewModels<ProductsViewModel>()

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var navController: NavController

    private lateinit var mView: View
    private lateinit var loading: LoadingScreen

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        mView = binding.root
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        attachViewModel()
    }

    private fun init() {
        navController = requireView().findNavController()
        loading = LoadingScreen(requireContext())
    }

    private fun attachViewModel() {
        viewModel.productScreenStateObserver.observe(viewLifecycleOwner) {
            handleState(it)
        }
    }

    private fun handleState(state: ProductScreenState) {
        when (state) {
            ProductScreenState.Idle -> {

            }

            ProductScreenState.GetProductDetailsInProgress -> {
                loading.displayLoading(false)
            }

            is ProductScreenState.GetProductDetailsInSuccessful -> {
                loading.hideLoading()
                clearState()
            }

            is ProductScreenState.GetProductDetailsInFailed -> {
                loading.hideLoading()
                clearState()
                showSnackBar(mView, state.instruction!!)
            }

            else -> {}
        }
    }

    private fun makeAPICall() {
        viewModel.changeScreenState(
            ProductScreenState.
            GetProductDetailsInRequestSend(1)
        )
    }

    private fun clearState() {
        viewModel.changeScreenState(
            ProductScreenState.Idle
        )
    }
}