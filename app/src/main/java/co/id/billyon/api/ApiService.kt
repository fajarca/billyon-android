package co.id.billyon.api

import co.id.billyon.model.request.AddProductRequest
import co.id.billyon.model.request.CategoryRequest
import co.id.billyon.model.request.LoginRequest
import co.id.billyon.model.response.AddProductResponse
import co.id.billyon.model.response.CategoryResponse
import co.id.billyon.model.response.LoginResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    @POST("user/login")
    fun login(@Body request : LoginRequest) : Observable<LoginResponse>

    @POST("category/add")
    fun uploadCategory(@Body request: List<CategoryRequest>) : Observable<CategoryResponse>

    @POST("product/add")
    fun uploadProduct(@Body request: List<AddProductRequest>) : Observable<AddProductResponse>
}