package co.id.billyon.repository.cashier.carts

import co.id.billyon.db.entity.Carts
import co.id.billyon.di.NetManager

class CartsRepository(private val localDataSource: CartsLocalDataSource, private val remoteDataSource: CartsRemoteDataSource, private val netManager: NetManager) {

    fun insertCart(cart : Carts) = localDataSource.insert(cart)
    fun delete(cart : Carts) = localDataSource.delete(cart)
    fun update(cart: Carts) = localDataSource.update(cart)
    //fun updateQuantity(productId: Long, quantity : Int) = localDataSource.updateQuantity(productId,quantity)
    fun findAll(isFinished : Boolean) = localDataSource.findAll(isFinished)

}