package co.id.billyon.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import co.id.billyon.api.ApiService
import co.id.billyon.db.BillyonDatabase
import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.db.entity.Products
import co.id.billyon.model.Product
import co.id.billyon.util.NetManager

class ProductRepository(private val productsDao : ProductsDao?) {

    val remoteDataSource = ProductRemoteDataSource()
    val localDataSource = ProductLocalDataSource()

    fun getAllProducts() : LiveData<List<Product>> {
        val data = MutableLiveData<List<Product>>()

        val productList = arrayListOf<Product>()
        productList.add(Product(1,1,"/haha",1,"Kopi Susu Kenangan",100,80,12000,8000,8000,60000,1))
        productList.add(Product(1,1,"/haha",1,"Kopi Susu Mantan",100,80,12000,8000,8000,60000,1))
        productList.add(Product(1,1,"/haha",1,"Kopi Susu Istri",100,80,12000,8000,8000,60000,1))
        data.value = productList

        return data
    }

    fun insertProduct(product: Products) {
        productsDao?.insert(product)
    }

    fun getAllProductFromDb() : LiveData<List<Products>>? {
        return productsDao?.getAllProduct()
    }

  /*  fun fetchProductFromServer(storeId : Int) : Observable<Product>{
        netManager.isConnectedToInternet?.let {
            if (it) {
                return
            }
        }

    }*/



}