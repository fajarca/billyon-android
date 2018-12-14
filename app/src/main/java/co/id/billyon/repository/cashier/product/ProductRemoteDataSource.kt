package co.id.billyon.repository.cashier.product

import co.id.billyon.api.ApiService
import co.id.billyon.model.request.AddProductRequest
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun uploadProduct(request : List<AddProductRequest>) = apiService.uploadProduct(request)


}