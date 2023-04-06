package com.solocatapps.mvvmproductlistapp.service

import com.solocatapps.mvvmproductlistapp.model.ProductResponse

sealed class NetworkState {
    data class Loading(val products: ProductResponse) : NetworkState()
    data class Success(val products: ProductResponse) : NetworkState()
    data class Error(val message: String) : NetworkState()
}
