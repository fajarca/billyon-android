package co.id.billyon.repository.cashier.carts

import co.id.billyon.db.entity.CartProducts
import co.id.billyon.db.entity.Carts
import co.id.billyon.di.NetManager

class CartsRepository(private val localDataSource: CartsLocalDataSource, private val remoteDataSource: CartsRemoteDataSource, private val netManager: NetManager) {

    fun createCart(cart : Carts) = localDataSource.createCart(cart)
    fun deleteCart(cart : Carts) = localDataSource.deleteCart(cart)
    fun updateCart(cart: Carts) = localDataSource.updateCart(cart)
    fun findAll(isFinished : Boolean) = localDataSource.findAll(isFinished)
    fun findActiveCartId(isFinished: Boolean) = localDataSource.findActiveCartId(isFinished)

    fun addProductToCart(cart : CartProducts) = localDataSource.addProductToCart(cart)
    fun deleteProductFromCart(productId: Long) = localDataSource.deleteProductFromCart(productId)
    fun updateProductQuantity(productId: Long, quantity : Int) = localDataSource.updateProductQuantity(productId,quantity)

    fun findAllProductsOnCart()  = localDataSource.findAllProductsOnCart()
}