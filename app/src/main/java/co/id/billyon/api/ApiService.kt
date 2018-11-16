package co.id.billyon.api

import co.id.billyon.model.PostsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    fun getPosts(): Observable<List<PostsResponse>>
}