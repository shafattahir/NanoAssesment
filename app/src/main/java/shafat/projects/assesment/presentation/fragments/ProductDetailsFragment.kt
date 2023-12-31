package shafat.projects.assesment.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import shafat.projects.assesment.R
import shafat.projects.assesment.databinding.FragmentProductDetailsBinding
import shafat.projects.assesment.datasource.beans.response.ProductResponseBean
import shafat.projects.assesment.presentation.states.ProductScreenState
import shafat.projects.assesment.presentation.viewmodels.ProductsViewModel
import shafat.projects.assesment.utils.LoadingScreen
import shafat.projects.assesment.utils.ProductDescriptionBottomSheet
import shafat.projects.assesment.utils.SavedData.BundleKeys.PRODUCT_ID
import shafat.projects.assesment.utils.showSnackBar

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private val viewModel by viewModels<ProductsViewModel>()

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var navController: NavController

    private lateinit var mView: View
    private lateinit var loading: LoadingScreen
    private lateinit var popupFragment : ProductDescriptionBottomSheet

    private lateinit var  productDetailObj: ProductResponseBean
    private var productID = -1

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
        getBundleData()
        attachViewModel()
        makeAPICall()
        attachClickListeners()
    }

    private fun attachClickListeners() {
        with(binding){
            btnBack.setOnClickListener {
                goBack()
            }
            showDialog.setOnClickListener {
                showDialogFragment()
            }
        }
    }

    private fun goBack() {
        if (this::popupFragment.isInitialized){
            popupFragment.dialog?.dismiss()
        }
        navController.popBackStack()
    }

    private fun getBundleData() {
        if (null != arguments) {
            if (arguments?.containsKey(PRODUCT_ID)!!)
                productID = arguments?.getInt(PRODUCT_ID)!!
        }
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
                productDetailObj = state.productDetailObj!!
                setupUI()
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

    private fun setupUI() {
        with(binding){
            Glide.with(requireContext())
                .load(productDetailObj.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemImage)
        }
        showDialogFragment()
    }

    private fun showDialogFragment() {
        popupFragment = ProductDescriptionBottomSheet(requireContext(),productDetailObj,
            onCanceled = {
                binding.showDialog.visibility = View.VISIBLE
            })
        if (this::mView.isInitialized) {
            mView.let {
                binding.showDialog.visibility = View.GONE
                popupFragment.show(
                    parentFragmentManager,
                    "ModalBottomSheet"
                )
            }
        }
    }

    private fun makeAPICall() {
        if (productID == -1) {
           goBack()
        } else {
            viewModel.changeScreenState(
                ProductScreenState.GetProductDetailsInRequestSend(productID)
            )
        }
    }

    private fun clearState() {
        viewModel.changeScreenState(
            ProductScreenState.Idle
        )
    }
}