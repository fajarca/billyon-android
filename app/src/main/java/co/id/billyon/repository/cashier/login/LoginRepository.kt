package co.id.billyon.repository.cashier.login

import co.id.billyon.di.NetManager
import javax.inject.Inject

class LoginRepository @Inject constructor(private val localDataSource: LoginLocalDataSource, private val remoteDataSource: LoginRemoteDataSource, private val netManager: NetManager) {

    fun setLoggedIn(isLoggedIn : Boolean) = localDataSource.setLoggedIn(isLoggedIn)
    fun isLoggedIn() = localDataSource.isLoggedIn()
    fun setAsCashier(isCashier : Boolean) = localDataSource.setAsCashier(isCashier)
    fun isCashier() = localDataSource.isCashier()

    fun login(username : String, password : String) = remoteDataSource.login(username, password)

}