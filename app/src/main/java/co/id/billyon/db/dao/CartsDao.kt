package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.Carts
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CartsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: Carts) : Long

    @Update
    fun update(cart: Carts)

    @Delete
    fun delete(cart: Carts)

    @Query("SELECT * FROM carts")
    fun findAll() : Flowable<List<Carts>>

    @Query("select * from carts where id  = :cartId")
    fun findProductByCartId(cartId : Int) : Flowable<List<Carts>>

    @Query("SELECT id FROM CARTS WHERE is_finished = :isFinished")
    fun findActiveCartId(isFinished : Boolean) : Maybe<Long>
}