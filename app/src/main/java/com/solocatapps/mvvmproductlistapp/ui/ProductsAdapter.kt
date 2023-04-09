package com.solocatapps.mvvmproductlistapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.solocatapps.mvvmproductlistapp.databinding.ProductRowBinding
import com.solocatapps.mvvmproductlistapp.model.Product
import com.solocatapps.mvvmproductlistapp.util.loadWithCoil

class ProductsAdapter(private val productListFragment: ProductListFragment) :  RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {
    
    companion object{
        const val TAG = "ProductsAdapter"
    }
    
    inner class MyViewHolder(val binding: ProductRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var products: List<Product>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ProductRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentProduct = products[position]
        val imageLoader = loadWithCoil(holder.itemView.context)

        holder.binding.apply {
            imageView.load(currentProduct.thumbnail, imageLoader)
            textViewTitle.text = currentProduct.title
            textViewDescription.text = currentProduct.description
            textViewPrice.text = currentProduct.price.toString() + "$"
        }

        holder.itemView.setOnClickListener() {
            productListFragment.moveToProductDetailFragment(currentProduct)
        }
    }

    override fun getItemCount() = products.size
}