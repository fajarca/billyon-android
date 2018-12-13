package co.id.billyon.repository.cashier.category

import co.id.billyon.api.ApiService
import co.id.billyon.model.request.CategoryRequest
import co.id.billyon.model.response.CategoryResponse
import io.reactivex.Observable
import javax.inject.Inject

class CategoryRemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun uploadCategory(categories: List<CategoryRequest>): Observable<CategoryResponse> {
        return apiService.uploadCategory(categories)
    }
}