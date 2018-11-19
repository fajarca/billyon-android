package co.id.billyon.repository

import co.id.billyon.api.ApiService
import co.id.billyon.db.dao.CategoryDao
import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.Products
import co.id.billyon.di.NetManager
import io.reactivex.Completable
import java.util.concurrent.ExecutorService

class ProductRepository(private val apiService: ApiService, private val productsDao : ProductsDao, private val categoryDao : CategoryDao, private val netManager: NetManager) {

    val remoteDataSource = ProductRemoteDataSource(apiService)
    val localDataSource = ProductLocalDataSource(productsDao, categoryDao)

    fun insertProduct(product: Products)  = localDataSource.insertProduct(product)
    fun insertCategory(category: Category) = localDataSource.insertCategory(category)

    fun insertAllProduct(products: List<Products>) = localDataSource.insertAllProduct(products)
    fun insertAllCategories(categories : List<Category>) = localDataSource.insertAllCategory(categories)

    fun delete(product: Products) = localDataSource.delete(product)
    fun deleteAll() = localDataSource.deleteAll()

    fun update(product: Products) = localDataSource.update(product)

    fun getAllProduct() =  localDataSource.getAllProduct()

    fun getAllPost() = if (netManager.isConnectedToInternet) remoteDataSource.fetchPost() else remoteDataSource.fetchPost()

    fun getAllCategories() = localDataSource.getAllCategories()
    fun getAllCategoriesWithProductCount() = categoryDao.getAllCategoryWithProductCount()


}