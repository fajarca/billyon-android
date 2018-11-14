package co.id.billyon.viewmodel

import android.arch.lifecycle.ViewModel
import co.id.billyon.db.entity.Products
import co.id.billyon.repository.ProductRepository
import javax.inject.Inject

class CashierDashboardViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    fun loadAllProducts() = repository.getAllProduct()
    fun insertProduct(product: Products) = repository.insert(product)
}