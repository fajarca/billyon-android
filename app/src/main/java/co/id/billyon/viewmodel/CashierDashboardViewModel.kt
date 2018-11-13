package co.id.billyon.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import co.id.billyon.db.BillyonDatabase
import co.id.billyon.db.entity.Products
import co.id.billyon.repository.ProductRepository
import java.util.concurrent.Executors
import javax.inject.Inject

class CashierDashboardViewModel @Inject constructor() : ViewModel() {

   /* val db = BillyonDatabase.getDatabase(application)
    val repository = ProductRepository(db.productDao())
    val executor = Executors.newSingleThreadExecutor()

    fun loadAllProducts() = repository.getAllProductFromDb()
    fun insertProduct() {
        val product = Products(5,1,1,"/haha","Kopi Susu Istri",100,80,12000,8000,8000,60000)
        executor.execute {
            repository.insertProduct(product)
        }
    }*/
}