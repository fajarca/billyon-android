package co.id.billyon.repository.cashier.carts

import co.id.billyon.db.dao.join.CartsAndCartProductsDao
import co.id.billyon.db.dao.CartsDao
import co.id.billyon.db.entity.Carts
import javax.inject.Inject

class CartsLocalDataSource @Inject constructor(private val dao: CartsDao, private val cartProductDaoCart : CartsAndCartProductsDao) {

    fun insert(cart : Carts) = dao.insert(cart)
    fun delete(cart : Carts) = dao.delete(cart)
    fun update(cart: Carts) = dao.update(cart)

    //fun updateQuantity(productId: Long, quantity : Int) = cartProductDaoCart.updateQuantity(productId, quantity)
    fun findAll(isFinished : Boolean) = cartProductDaoCart.getCartsAndProducts(isFinished)

}
