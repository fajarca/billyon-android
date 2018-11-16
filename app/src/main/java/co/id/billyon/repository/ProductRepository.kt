package co.id.billyon.repository

import co.id.billyon.api.ApiService
import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.db.entity.Products
import co.id.billyon.di.NetManager
import java.util.concurrent.ExecutorService

class ProductRepository(private val apiService: ApiService, private val productsDao : ProductsDao, private val executor: ExecutorService, private val netManager: NetManager) {

    val remoteDataSource = ProductRemoteDataSource(apiService)
    val localDataSource = ProductLocalDataSource(productsDao, executor)

    /*fun getAllProducts() : LiveData<List<Product>> {
        val data = MutableLiveData<List<Product>>()

        val productList = arrayListOf<Product>()
        productList.add(Product(1,1,"/haha",1,"Kopi Susu Kenangan",100,80,12000,8000,8000,60000,1))
        productList.add(Product(1,1,"/haha",1,"Kopi Susu Mantan",100,80,12000,8000,8000,60000,1))
        productList.add(Product(1,1,"/haha",1,"Kopi Susu Istri",100,80,12000,8000,8000,60000,1))
        data.value = productList
        return data
    }*/

    fun insert(product: Products) = localDataSource.insertProduct(product)

    fun getAllProduct() =  localDataSource.getAllProduct()

    fun getAllPost() = if (netManager.isConnectedToInternet) remoteDataSource.fetchPost() else remoteDataSource.fetchPost()


}