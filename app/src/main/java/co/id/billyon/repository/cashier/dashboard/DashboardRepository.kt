package co.id.billyon.repository.cashier.dashboard

import co.id.billyon.db.entity.Category
import co.id.billyon.di.NetManager

class DashboardRepository(private val localDataSource: DashboardLocalDataSource, private val remoteDataSource: DashboardRemoteDataSource, private val netManager: NetManager) {


    fun insertCategory(category: Category) = localDataSource.insertCategory(category)
    fun insertAllCategories(categories : List<Category>) = localDataSource.insertAllCategory(categories)

    fun getAllCategories() = localDataSource.getAllCategories()
    fun getAllCategoriesWithProductCount() = localDataSource.getAllCategoriesWithProductCount()
    fun getAllCategoriesWithProduct() = localDataSource.getAllCategoriesWithProduct()

    fun deleteCategory(category: Category) = localDataSource.deleteCategory(category)

}