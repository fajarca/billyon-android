package co.id.billyon.viewmodel

import android.arch.lifecycle.ViewModel
import co.id.billyon.db.entity.Products
import co.id.billyon.repository.ProductRepository
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class CashierDashboardViewModel @Inject constructor(private val repository: ProductRepository, private val executor: ExecutorService) : ViewModel() {

    fun loadAllProducts() = repository.getAllProductFromDb()
    fun insertProduct() {
        val product = Products(5,1,1,"/haha","Kopi Susu Istri",100,80,12000,8000,8000,60000)
        executor.execute {
            repository.insertProduct(product)
        }
    }
}