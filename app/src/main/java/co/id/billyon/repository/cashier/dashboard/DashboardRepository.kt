package co.id.billyon.repository.cashier.dashboard

import co.id.billyon.api.ApiService
import co.id.billyon.db.dao.CategoryDao
import co.id.billyon.db.entity.Category
import co.id.billyon.di.NetManager

class DashboardRepository(private val apiService: ApiService, private val categoryDao : CategoryDao, private val netManager: NetManager) {

    val localDataSource = DashboardLocalDataSource(categoryDao)
    val remoteDataSource = DashboardRemoteDataSource(apiService)

    fun insertCategory(category: Category) = localDataSource.insertCategory(category)
    fun insertAllCategories(categories : List<Category>) = localDataSource.insertAllCategory(categories)

    fun getAllCategories() = localDataSource.getAllCategories()
    fun getAllCategoriesWithProductCount() = localDataSource.getAllCategoriesWithProductCount()

    fun deleteCategory(category: Category) = localDataSource.deleteCategory(category)

}