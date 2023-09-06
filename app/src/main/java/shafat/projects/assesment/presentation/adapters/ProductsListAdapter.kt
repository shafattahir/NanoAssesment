package shafat.projects.assesment.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import shafat.projects.assesment.R
import shafat.projects.assesment.databinding.ItemProductListBinding
import shafat.projects.assesment.datasource.beans.response.ProductResponseBean

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