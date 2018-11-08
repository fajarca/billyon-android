package co.id.billyon.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import co.id.billyon.db.BillyonDatabase
import co.id.billyon.db.entity.Products
import co.id.billyon.repository.ProductRepository
import java.util.concurrent.Executors

class CashierDashboardViewModel(application: Application) : AndroidViewModel(application) {
    val isLoading = ObservableField<Boolean>()

    val db = BillyonDatabase.getInstance(application)
    val repository = ProductRepository(db?.ProductDao())
    val executor = Executors.newSingleThreadExecutor()

    fun loadAllProducts() = repository.getAllProductFromDb()
    fun insertProduct() {
        val product = Products(4,1,1,"/haha","Kopi Susu Istri",100,80,12000,8000,8000,60000)
        executor.execute {
            repository.insertProduct(product)
        }
    }
}