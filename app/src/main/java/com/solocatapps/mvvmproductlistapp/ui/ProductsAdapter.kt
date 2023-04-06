package com.solocatapps.mvvmproductlistapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.solocatapps.mvvmproductlistapp.databinding.CommonStandingsRowBinding
import com.solocatapps.mvvmproductlistapp.model.Product

class ProductsAdapter(private val productListFragment: ProductListFragment) :  RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: CommonStandingsRowBinding) :
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
            CommonStandingsRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentProduct = products[position]
        holder.binding.apply {
            //val imageLoaderForSvg = loadWithCoil(holder.itemView.context)

            holder.itemView.setOnClickListener() {
                //leagueDetailsStandingsFragment.moveToTeamDetailsFragment(currentLeague.team.id)
            }
        }
    }

    override fun getItemCount() = products.size
}