package com.solocatapps.mvvmproductlistapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solocatapps.mvvmproductlistapp.R
import com.solocatapps.mvvmproductlistapp.databinding.FragmentProductListBinding

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

        binding.rvProductList.apply {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(false)
            /**
             * if your adapter is always empty, using this policy can help prevent unnecessary memory usage,
             * as the state of the RecyclerView will not be stored in memory.
             */
            productsAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        viewModel.responseProducts.observe(viewLifecycleOwner, Observer { listProducts ->
            productsAdapter.products = listProducts
        })
    }
}