package com.mrecun.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrecun.databinding.ItemProductBinding
import com.mrecun.model.Product
import com.mrecun.model.SaleState
import com.mrecun.model.Status

class RecyclerAdapter : ListAdapter<Product, RecyclerAdapter.ProductViewHolder>(COMPARE) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position) as Product
        holder.setData(product)
    }

    companion object {
        private val COMPARE = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem == newItem
        }
    }

    class ProductViewHolder(private val binding : ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(product: Product){
            binding.product = product
            val status = SaleState.convert(product.status)
            binding.sold = when(status){
                Status.sold_out -> true
                else -> false
            }
        }
    }



}