package co.id.billyon.repository.cashier.login

import co.id.billyon.api.ApiService
import co.id.billyon.model.request.LoginRequest
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun login(username : String, password : String)  = apiService.login(LoginRequest(username, password))
}