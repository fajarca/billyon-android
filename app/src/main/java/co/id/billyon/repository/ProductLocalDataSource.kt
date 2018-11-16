package co.id.billyon.repository

import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.db.entity.Products

class ProductLocalDataSource(private val productsDao: ProductsDao) {

    fun insertProduct(product: Products) = productsDao.insert(product)

    fun getAllProduct() =  productsDao.getAllProduct()
}