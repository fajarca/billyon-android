package co.id.billyon.repository.cashier.carts

import co.id.billyon.db.dao.CartProductsDao
import co.id.billyon.db.dao.CartsDao
import co.id.billyon.db.dao.join.CartsAndCartProductsDao
import co.id.billyon.db.entity.CartProducts
import co.id.billyon.db.entity.Carts
import javax.inject.Inject

class CartsLocalDataSource @Inject constructor(private val dao: CartsDao, private val cartProductDao : CartProductsDao, private val cartAndCartProductDao : CartsAndCartProductsDao) {

    fun createCart(cart : Carts) = dao.insert(cart)
    fun deleteCart(cart : Carts) = dao.delete(cart)
    fun updateCart(cart: Carts) = dao.update(cart)
    fun findActiveCartId(isFinished: Boolean) = dao.findActiveCartId(isFinished)

    fun addProductToCart(cart : CartProducts) = cartProductDao.insert(cart)
    fun deleteProductFromCart(productId : Long) = cartProductDao.delete(productId)
    fun updateProductQuantity(productId: Long, quantity : Int) = cartProductDao.updateQuantity(productId,quantity)

    //fun updateQuantity(productId: Long, quantity : Int) = cartProductDaoCart.updateQuantity(productId, quantity)
    fun findAll(isFinished : Boolean) = cartAndCartProductDao.getCartsAndProducts(isFinished)
    fun findAllProductsOnCart()  = cartAndCartProductDao.getItemCountAndTotalPrice()
}
