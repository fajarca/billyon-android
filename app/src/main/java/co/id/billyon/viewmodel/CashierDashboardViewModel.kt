package co.id.billyon.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import co.id.billyon.model.Product
import co.id.billyon.model.ProductsModel
import co.id.billyon.repository.ProductRepository

class CashierDashboardViewModel : ViewModel() {
    val productsModel = ProductsModel()
    val isLoading = ObservableField<Boolean>()
    var products = MutableLiveData<ArrayList<Product>>()

    val repository = ProductRepository()

    fun loadProducts() {
        isLoading.set(true)
        productsModel.getProducts(object : ProductsModel.onProductsReadyCallback {
            override fun onDataReady(data: ArrayList<Product>) {
                isLoading.set(false)
                products.value = data
            }
        })
    }

    fun loadAllProducts() = repository.getAllProducts()
}