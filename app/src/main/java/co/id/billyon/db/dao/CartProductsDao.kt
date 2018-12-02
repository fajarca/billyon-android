package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.CartProducts
import co.id.billyon.db.entity.join.CartsAndCartProducts
import io.reactivex.Flowable

@Dao
interface CartProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: CartProducts)

    @Update
    fun update(cart: CartProducts)

    @Delete
    fun delete(cart: CartProducts)

    @Query("SELECT * FROM cart_products")
    fun findAll() : Flowable<List<CartProducts>>

    /*@Query("UPDATE carts_products SET quantity = :quantity ")
    fun updateQuantity(productId : Long, quantity : Int)*/

}