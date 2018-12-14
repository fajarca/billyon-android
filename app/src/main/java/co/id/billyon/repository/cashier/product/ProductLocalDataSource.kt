package co.id.billyon.repository.cashier.product

import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.db.entity.Products
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(private val productsDao: ProductsDao) {

    fun getAllProduct() =  productsDao.getAll()
    fun getAllUnsyncronizedProduct() = productsDao.getAllUnsyncronizedProduct()

    fun findProductByCategoryId(categoryId : Int) = productsDao.getProductByCategoryId(categoryId)

    fun insertProduct(product: Products) = productsDao.insert(product)

    fun insertAllProduct(products: List<Products>) = productsDao.insertAll(products)

    fun delete(product: Products) = productsDao.delete(product)
    fun deleteAll() = productsDao.deleteAll()

    fun update(product: Products) = productsDao.update(product)

}