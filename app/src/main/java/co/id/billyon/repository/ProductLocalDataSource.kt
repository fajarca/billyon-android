package co.id.billyon.repository

import android.util.Log
import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.db.entity.Products
import java.util.concurrent.ExecutorService

class ProductLocalDataSource(private val productsDao: ProductsDao, private val executor : ExecutorService) {

    fun insertProduct(product: Products) {
//        val product = Products(5,1,1,"/haha","Kopi Susu Istri",100,80,12000,8000,8000,60000)
        executor.execute {
            productsDao.insert(product)
        }
    }

    fun getAllProduct() =  productsDao.getAllProduct()
}