package co.id.billyon.repository.cashier.dashboard

import co.id.billyon.db.entity.Category
import co.id.billyon.di.NetManager
import co.id.billyon.model.CategoryRequest
import co.id.billyon.model.CategoryResponse
import io.reactivex.Observable

class CategoryRepository(private val localDataSource: CategoryLocalDataSource, private val remoteDataSource: CategoryRemoteDataSource, private val netManager: NetManager) {


    fun insertCategory(category: Category) = localDataSource.insertCategory(category)
    fun insertAllCategories(categories : List<Category>) = localDataSource.insertAllCategory(categories)

    fun getAllCategories() = localDataSource.getAllCategories()
    fun getProductCountOnCart() = localDataSource.getProductCountOnCart()

    fun getAllCategoriesWithProductCount() = localDataSource.getAllCategoriesWithProductCount()

    fun deleteCategory(categoryId: Long) = localDataSource.deleteCategory(categoryId)

    fun uploadCategory(category: CategoryRequest) : Observable<CategoryResponse> {
        val categories = arrayListOf<CategoryRequest>()
        categories.add(category)
        return remoteDataSource.uploadCategory(categories)
    }

}