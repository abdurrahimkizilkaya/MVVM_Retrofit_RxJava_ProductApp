package com.solocatapps.mvvmproductlistapp.service

import com.solocatapps.mvvmproductlistapp.model.ProductResponse
import com.solocatapps.mvvmproductlistapp.util.Constants.GET_ALL_PRODUCTS
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ProductApi {

    @GET(GET_ALL_PRODUCTS)
    fun getAllProducts() : Single<ProductResponse>
}