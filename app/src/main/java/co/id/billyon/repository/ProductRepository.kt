package co.id.billyon.repository

import co.id.billyon.api.ApiService
import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.db.entity.Products
import co.id.billyon.di.NetManager
import io.reactivex.Completable
import java.util.concurrent.ExecutorService

class ProductRepository(private val apiService: ApiService, private val productsDao : ProductsDao, private val netManager: NetManager) {

    val remoteDataSource = ProductRemoteDataSource(apiService)
    val localDataSource = ProductLocalDataSource(productsDao)

    fun insertProduct(product: Products)  = localDataSource.insertProduct(product)
    fun insertAllProduct(products: List<Products>) = localDataSource.insertAllProduct(products)

    fun delete(product: Products) = localDataSource.delete(product)
    fun deleteAll() = localDataSource.deleteAll()

    fun update(product: Products) = localDataSource.update(product)

    fun getAllProduct() =  localDataSource.getAllProduct()

    fun getAllPost() = if (netManager.isConnectedToInternet) remoteDataSource.fetchPost() else remoteDataSource.fetchPost()


}