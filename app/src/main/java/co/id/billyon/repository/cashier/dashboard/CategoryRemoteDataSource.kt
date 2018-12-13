package co.id.billyon.repository.cashier.dashboard

import co.id.billyon.api.ApiService
import co.id.billyon.db.entity.Category
import co.id.billyon.model.CategoryRequest
import co.id.billyon.model.CategoryResponse
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class CategoryRemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun uploadCategory(categories: List<CategoryRequest>): Observable<CategoryResponse> {
        return apiService.uploadCategory(categories)
    }
}