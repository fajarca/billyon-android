package co.id.billyon.repository.cashier.dashboard

import co.id.billyon.db.dao.CategoryAndProductsDao
import co.id.billyon.db.dao.CategoryDao
import co.id.billyon.db.entity.Category
import javax.inject.Inject

class DashboardLocalDataSource @Inject constructor(private val categoryDao : CategoryDao, private val categoryProductDao : CategoryAndProductsDao) {

    fun getAllCategories() = categoryDao.getAllCategory()
    fun getAllCategoriesWithProductCount() = categoryDao.getAllCategoryWithProductCount()
    fun getAllCategoriesWithProduct() = categoryProductDao.getCategoryAndProducts()

    fun insertCategory(category : Category) = categoryDao.insert(category)
    fun insertAllCategory(categories : List<Category>) = categoryDao.insertAll(categories)

    fun deleteCategory(category: Category) = categoryDao.delete(category)
}