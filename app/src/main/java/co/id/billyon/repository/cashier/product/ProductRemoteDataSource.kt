package co.id.billyon.repository.cashier.product

import co.id.billyon.api.ApiService
import co.id.billyon.model.PostsResponse
import co.id.billyon.util.TMDB_API_KEY
import io.reactivex.Observable

class ProductRemoteDataSource(private val apiService: ApiService) {

    fun fetchPost() = apiService.getPosts()


}