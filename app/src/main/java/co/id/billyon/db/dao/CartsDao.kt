package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.Carts
import io.reactivex.Flowable

@Dao
interface CartsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: Carts)

    @Update
    fun update(cart: Carts)

    @Query("UPDATE carts SET quantity = :quantity WHERE product_id = :productId")
    fun updateQuantity(productId: Long, quantity : Int)

    @Delete
    fun delete(cart: Carts)

    @Query("SELECT * FROM carts")
    fun findAll() : Flowable<List<Carts>>

    @Query("select * from carts where id  = :cartId")
    fun findProductByCartId(cartId : Int) : Flowable<List<Carts>>
}