package shafat.projects.assesment.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import shafat.projects.assesment.R
import shafat.projects.assesment.databinding.ProductDescSheetBinding
import shafat.projects.assesment.datasource.beans.response.ProductResponseBean


class ProductDescriptionBottomSheet(
    private val context: Context,
    private val productDetailObj:
    ProductResponseBean,
    private val onCanceled: () -> Unit
) :
    BottomSheetDialogFragment(R.layout.product_desc_sheet) {

    private lateinit var binding: ProductDescSheetBinding
    private var isRatingShown = false
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
        (dialog as BottomSheetDialog).behavior.peekHeight = 350
        (dialog as BottomSheetDialog).behavior.isHideable = false

        with(binding) {
            val ratingValueMessage = "Reviews (${productDetailObj.rating?.count})"
            itemDesc.text = productDetailObj.description
            itemTitle.text = productDetailObj.title
            ratingCount.text = ratingValueMessage
            ratingValue.text = productDetailObj.rating?.rate.toString()
            rBar.rating = productDetailObj.rating?.rate ?: 0F
            itemPrice.setPrice(productDetailObj.price.toString())
        }
    }

    private fun setClickListeners() {
        binding.ivToggle.setOnClickListener {
            val icDown = ResourcesCompat.getDrawable(resources, R.drawable.ic_down, null)
            val icUp = ResourcesCompat.getDrawable(resources, R.drawable.ic_up, null)
            if (isRatingShown.not()) {
                isRatingShown = true
                binding.ivToggle.setImageDrawable(icDown)
                binding.ratingBlock.visibility = View.VISIBLE
            } else {
                isRatingShown = false
                binding.ivToggle.setImageDrawable(icUp)
                binding.ratingBlock.visibility = View.GONE
            }
        }

        binding.btnShare.setOnClickListener {
            shareWithFriends(productDetailObj.title,context)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onCanceled()
    }
}