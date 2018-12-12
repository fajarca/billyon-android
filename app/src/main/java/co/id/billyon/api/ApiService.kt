package co.id.billyon.api

import co.id.billyon.model.CategoryRequest
import co.id.billyon.model.LoginRequest
import co.id.billyon.model.LoginResponse
import co.id.billyon.model.PostsResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @GET("posts")
    fun getPosts(): Observable<List<PostsResponse>>

    @POST("/user/login")
    fun login(@Body request : LoginRequest) : Observable<LoginResponse>

    @POST("/category/add")
    fun uploadCategory(@Body request: CategoryRequest) : Observable<CategoryRequest>

}