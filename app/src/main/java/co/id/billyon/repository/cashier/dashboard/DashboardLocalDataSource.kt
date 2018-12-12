package co.id.billyon.repository.cashier.dashboard

import co.id.billyon.db.dao.CartProductsDao
import co.id.billyon.db.dao.join.CategoryAndProductsDao
import co.id.billyon.db.dao.CategoryDao
import co.id.billyon.db.entity.Category
import io.reactivex.Flowable
import javax.inject.Inject

class DashboardLocalDataSource @Inject constructor(private val categoryDao : CategoryDao, private val categoryProductDao : CategoryAndProductsDao, private val cartProductsDao: CartProductsDao) {

    fun getAllCategories() = categoryDao.getAllCategory()
    fun getAllCategoriesWithProductCount() = categoryDao.getAllCategoryWithProductCount()
    fun getAllCategoriesWithProduct() = categoryProductDao.getCategoryAndProducts()

    fun insertCategory(category : Category) = categoryDao.insert(category)
    fun insertAllCategory(categories : List<Category>) = categoryDao.insertAll(categories)

    fun deleteCategory(categoryId: Long) = categoryDao.delete(categoryId)

    fun getProductCountOnCart() = cartProductsDao.getProductCountOnCart()
}