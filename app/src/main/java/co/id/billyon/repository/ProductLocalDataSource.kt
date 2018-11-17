package co.id.billyon.repository

import co.id.billyon.db.dao.CategoryDao
import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.Products

class ProductLocalDataSource(private val productsDao: ProductsDao, private val categoryDao: CategoryDao) {

    fun getAllProduct() =  productsDao.getAll()
    fun getAllCategories() = categoryDao.getAllCategory()

    fun insertProduct(product: Products) = productsDao.insert(product)
    fun insertCategory(category : Category) = categoryDao.insert(category)

    fun insertAllProduct(products: List<Products>) = productsDao.insertAll(products)
    fun insertAllCategory(categories : List<Category>) = categoryDao.insertAll(categories)

    fun delete(product: Products) = productsDao.delete(product)
    fun deleteAll() = productsDao.deleteAll()

    fun update(product: Products) = productsDao.update(product)


}