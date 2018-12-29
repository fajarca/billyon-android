package co.id.billyon.repository.cashier.cartproducts

import co.id.billyon.db.dao.CartProductsDao
import co.id.billyon.db.dao.CartsDao
import co.id.billyon.db.dao.join.CartsAndCartProductsDao
import javax.inject.Inject

class CartProductsLocalDataSource @Inject constructor(private val dao: CartProductsDao) {

    fun getAllProductOnCart(cartId : Int) = dao.findAllProductOnCart(cartId)
}