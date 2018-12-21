package co.id.billyon.db.dao

import androidx.room.*
import co.id.billyon.db.entity.CartProducts
import co.id.billyon.db.entity.join.CartsAndCartProducts
import io.reactivex.Flowable

@Dao
interface CartProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: CartProducts)

    @Update
    fun update(cart: CartProducts)

    @Query("DELETE FROM cart_products WHERE product_id = :productId")
    fun delete(productId: Long)

    @Query("SELECT * FROM cart_products")
    fun findAll() : Flowable<List<CartProducts>>

    @Query("UPDATE cart_products SET quantity = :quantity WHERE product_id = :productId")
    fun updateQuantity(productId : Long, quantity : Int)

    @Query("SELECT SUM(quantity) FROM cart_products")
    fun getProductCountOnCart() : Flowable<Int>

}