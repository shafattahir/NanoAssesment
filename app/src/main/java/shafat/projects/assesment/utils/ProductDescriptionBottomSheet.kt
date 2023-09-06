package shafat.projects.assesment.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import shafat.projects.assesment.R
import shafat.projects.assesment.databinding.ProductDescSheetBinding
import shafat.projects.assesment.datasource.beans.response.ProductResponseBean


class ProductDescriptionBottomSheet(private val mContext: Context,
                                    val productDetailObj: ProductResponseBean) :
    BottomSheetDialogFragment(R.layout.product_desc_sheet) {

    private lateinit var binding: ProductDescSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductDescSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()

        with(binding){
            itemDesc.text = productDetailObj.description
            itemTitle.text = productDetailObj.title

            ratingCount.text = "Reviews (${productDetailObj.rating?.count})"
            ratingValue.text = productDetailObj.rating?.rate.toString()
            rBar.rating = productDetailObj.rating?.rate?:0F
        }
    }

    private fun setClickListeners() {

    }
}