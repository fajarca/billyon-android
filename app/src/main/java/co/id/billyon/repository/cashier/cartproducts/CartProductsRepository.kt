package co.id.billyon.repository.cashier.cartproducts

import co.id.billyon.di.NetManager


class CartProductsRepository(private val localDataSource: CartProductsLocalDataSource, private val remoteDataSource: CartProductsRemoteDataSource, private val netManager: NetManager) {

    fun getProductsOnCartFromDb(cartId : Int)  = localDataSource.getAllProductOnCart(cartId)

}