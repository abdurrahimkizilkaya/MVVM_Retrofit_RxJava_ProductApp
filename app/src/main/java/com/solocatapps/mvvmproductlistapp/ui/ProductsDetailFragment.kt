package com.solocatapps.mvvmproductlistapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.solocatapps.mvvmproductlistapp.databinding.FragmentProductDetailBinding
import com.solocatapps.mvvmproductlistapp.model.Product
import com.solocatapps.mvvmproductlistapp.util.loadWithCoil

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val args : ProductDetailFragmentArgs by navArgs()

    companion object {
        private const val TAG = "ProductDetailFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        setupUI(product)
    }

    private fun setupUI(product : Product){

        val imageLoader = loadWithCoil(requireContext())

        binding.apply {
            textCategory.text = product.category
            imageProduct.load(product.images[0], imageLoader)
            textTitle.text = product.title
            textDescription.text = product.description
            textPrice.text = product.price.toString() + "$"
        }

    }
}