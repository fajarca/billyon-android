package co.id.billyon.repository.cashier.product

import co.id.billyon.db.entity.Products
import co.id.billyon.di.NetManager

class ProductRepository(private val localDataSource: ProductLocalDataSource, private val remoteDataSource: ProductRemoteDataSource, private val netManager: NetManager) {

    fun insertProduct(product: Products)  = localDataSource.insertProduct(product)

    fun insertAllProduct(products: List<Products>) = localDataSource.insertAllProduct(products)

    fun delete(product: Products) = localDataSource.delete(product)
    fun deleteAll() = localDataSource.deleteAll()

    fun update(product: Products) = localDataSource.update(product)

    fun getAllProduct() =  localDataSource.getAllProduct()

    fun findProductFromCategory(categoryId : Int) = localDataSource.findProductByCategoryId(categoryId)

}