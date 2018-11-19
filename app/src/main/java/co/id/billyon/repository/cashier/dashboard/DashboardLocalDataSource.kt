package co.id.billyon.repository.cashier.dashboard

import co.id.billyon.db.dao.CategoryDao
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.Products

class DashboardLocalDataSource constructor(private val categoryDao : CategoryDao) {

    fun getAllCategories() = categoryDao.getAllCategory()
    fun getAllCategoriesWithProductCount() = categoryDao.getAllCategoryWithProductCount()

    fun insertCategory(category : Category) = categoryDao.insert(category)
    fun insertAllCategory(categories : List<Category>) = categoryDao.insertAll(categories)

    fun deleteCategory(category: Category) = categoryDao.delete(category)
}