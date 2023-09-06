package shafat.projects.assesment.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import shafat.projects.assesment.R
import shafat.projects.assesment.databinding.FragmentProductsListBinding
import shafat.projects.assesment.datasource.beans.response.ProductResponseBean
import shafat.projects.assesment.presentation.adapters.ProductsListAdapter
import shafat.projects.assesment.presentation.states.ProductScreenState
import shafat.projects.assesment.presentation.viewmodels.ProductsViewModel
import shafat.projects.assesment.utils.LoadingScreen
import shafat.projects.assesment.utils.showSnackBar

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AllProductsFragment : Fragment(R.layout.fragment_products_list) {

    private val viewModel by viewModels<ProductsViewModel>()

    private lateinit var binding: FragmentProductsListBinding
    private lateinit var navController: NavController
    lateinit var adapter: ProductsListAdapter

    private lateinit var mView: View
    private lateinit var loading: LoadingScreen

    private lateinit var dataList: List<ProductResponseBean>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsListBinding.inflate(inflater, container, false)
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

            ProductScreenState.GetAllProductsInProgress -> {
                loading.displayLoading(false)
            }

            is ProductScreenState.GetAllProductsInSuccessful -> {
                loading.hideLoading()
                clearState()
            }

            is ProductScreenState.GetAllProductsInsFailed -> {
                loading.hideLoading()
                clearState()
                showSnackBar(mView, state.instruction!!)
            }

            else -> {}
        }
    }

    private fun makeAPICall() {
        viewModel.changeScreenState(
            ProductScreenState
                .GetAllProductsRequestSend
        )
    }

    private fun clearState() {
        viewModel.changeScreenState(
            ProductScreenState.Idle
        )
    }

    private fun setAdapterSearchToRec() {
        adapter = ProductsListAdapter(requireContext(),
            dataList, onItemClicked = { productID ->
                showProductDetails(productID)
            })
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvProducts.adapter = adapter
        binding.rvProducts.layoutManager = layoutManager
    }

    private fun showProductDetails(productID: Int) {

    }
}