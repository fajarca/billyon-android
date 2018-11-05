package co.id.billyon.repository

import co.id.billyon.api.ApiService
import co.id.billyon.util.NetManager

class ProductRepository(val netManager: NetManager, val apiService : ApiService) {

    val remoteDataSource = ProductRemoteDataSource()
    val localDataSource = ProductLocalDataSource()


  /*  fun fetchProductFromServer(storeId : Int) : Observable<Product>{
        netManager.isConnectedToInternet?.let {
            if (it) {
                return
            }
        }

    }*/



}