package com.solocatapps.mvvmproductlistapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solocatapps.mvvmproductlistapp.model.Product
import com.solocatapps.mvvmproductlistapp.model.ProductResponse
import com.solocatapps.mvvmproductlistapp.service.ProductApi
import com.solocatapps.mvvmproductlistapp.service.ProductApiClient
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductsViewModel(/*private val productApi : ProductApi,
                        private val disposable : CompositeDisposable*/)
    : ViewModel() {

    companion object {
        private const val TAG = "ProductViewModel"
    }

    private val productApi = ProductApiClient()
    private val disposable = CompositeDisposable()

    private val _responseLoading = MutableLiveData<Boolean>()
    val responseLoading: LiveData<Boolean>
        get() = _responseLoading

    private val _response = MutableLiveData<List<Product>>()
    val responseProducts: LiveData<List<Product>>
        get() = _response

    fun getAllProducts() {
        _responseLoading.postValue(true)
        try {
            disposable.add(
                productApi.getProductApi().getAllProducts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { response ->
                            _responseLoading.postValue(false)
                            val productsList = ArrayList<Product>()

                            for(product in response.products)
                                productsList.add(product)

                            _response.postValue(productsList)
                        },
                        {error ->
                            _responseLoading.postValue(false)
                        }
                    )
            )
        }
        catch(e : Exception) {
            Log.d(TAG, e.toString())
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}