package co.id.billyon.repository

import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.db.entity.Products

class ProductLocalDataSource(private val productsDao: ProductsDao) {

    fun getAllProduct() =  productsDao.getAll()

    fun insertProduct(product: Products) = productsDao.insert(product)
    fun insertAllProduct(products: List<Products>) = productsDao.insertAll(products)
    fun delete(product: Products) = productsDao.delete(product)
    fun deleteAll() = productsDao.deleteAll()
    fun update(product: Products) = productsDao.update(product)


}