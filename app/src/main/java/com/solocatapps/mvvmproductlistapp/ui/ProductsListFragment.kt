package com.solocatapps.mvvmproductlistapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solocatapps.mvvmproductlistapp.R
import com.solocatapps.mvvmproductlistapp.databinding.FragmentProductListBinding
import com.solocatapps.mvvmproductlistapp.model.Product

class ProductListFragment : Fragment() {

    lateinit var viewModel: ProductsViewModel
    private lateinit var productsAdapter: ProductsAdapter
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "ProductListFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productsAdapter = ProductsAdapter(this)
        viewModel.getAllProducts()

        setupRecyclerView()
        setupObservables()
    }

    private fun setupRecyclerView(){
        binding.rvProductList.apply {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(false)
        }
    }

    private fun setupObservables(){
        viewModel.responseLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.responseProducts.observe(viewLifecycleOwner) { listProducts ->
            productsAdapter.products = listProducts
        }
    }

    fun moveToProductDetailFragment(product : Product){
        val action = ProductListFragmentDirections.actionListFragmentToDetailFragment(product)
        findNavController().navigate(action)
    }
}