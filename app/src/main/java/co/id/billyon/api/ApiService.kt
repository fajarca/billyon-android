package co.id.billyon.api

import co.id.billyon.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @GET("posts")
    fun getPosts(): Observable<List<PostsResponse>>

    @POST("/user/login")
    fun login(@Body request : LoginRequest) : Observable<LoginResponse>

    @POST("/category/add")
    fun uploadCategory(@Body request: List<CategoryRequest>) : Observable<CategoryResponse>

}