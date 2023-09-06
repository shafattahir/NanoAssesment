package shafat.projects.assesment.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import shafat.projects.assesment.R
import shafat.projects.assesment.databinding.ItemProductListBinding
import shafat.projects.assesment.datasource.beans.response.ProductResponseBean
import shafat.projects.assesment.utils.setPrice

class ProductsListAdapter(
    val context: Context,
    private val dataList: List<ProductResponseBean>,
    private val onItemClicked: (productID: Int)
    -> Unit
) : RecyclerView.Adapter<ProductsListAdapter.ListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        return ListHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {

        with(holder.itemBinding){
            itemTitle.text = dataList[position].title
            itemDesc.text = dataList[position].description
            itemPrice.setPrice(dataList[position].price.toString())
            rBar.rating = dataList[position].rating?.rate?:0F

            Glide.with(context)
                .load(dataList[position].image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemImage)
        }
        holder.itemView.setOnClickListener {
            onItemClicked(dataList[position].id ?: 0)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ListHolder(itemBinding: ItemProductListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var itemBinding: ItemProductListBinding

        init {
            this.itemBinding = itemBinding
        }
    }
}