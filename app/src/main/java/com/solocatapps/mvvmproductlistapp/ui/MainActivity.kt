package com.solocatapps.mvvmproductlistapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.solocatapps.mvvmproductlistapp.R
import com.solocatapps.mvvmproductlistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFragment()
    }

    private fun setupFragment() {
        // Get the fragment container from the layout
        val fragmentContainer = binding.fragmentContainer
        // Create a new instance of the fragment
        val productListFragment = ProductListFragment()

        // Add the fragment to the container using a FragmentManager and a FragmentTransaction
        supportFragmentManager.beginTransaction()
            .add(fragmentContainer.id, productListFragment)
            .commit()
    }
}