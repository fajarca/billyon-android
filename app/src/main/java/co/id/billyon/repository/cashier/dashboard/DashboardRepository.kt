package co.id.billyon.repository.cashier.dashboard

import co.id.billyon.db.entity.Category
import co.id.billyon.di.NetManager
import io.reactivex.Flowable

class DashboardRepository(private val localDataSource: DashboardLocalDataSource, private val remoteDataSource: DashboardRemoteDataSource, private val netManager: NetManager) {


    fun insertCategory(category: Category) = localDataSource.insertCategory(category)
    fun insertAllCategories(categories : List<Category>) = localDataSource.insertAllCategory(categories)

    fun getAllCategories() = localDataSource.getAllCategories()
    fun getProductCountOnCart() = localDataSource.getProductCountOnCart()

    fun getAllCategoriesWithProductCount() = localDataSource.getAllCategoriesWithProductCount()
    fun getAllCategoriesWithProduct() = localDataSource.getAllCategoriesWithProduct()

    fun deleteCategory(categoryId: Long) = localDataSource.deleteCategory(categoryId)

}