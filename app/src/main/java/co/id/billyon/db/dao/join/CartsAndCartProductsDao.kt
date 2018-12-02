package co.id.billyon.db.dao.join

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import co.id.billyon.db.entity.join.CartsAndCartProducts
import io.reactivex.Flowable

@Dao
interface CartsAndCartProductsDao {
    @Query("SELECT * from carts where is_finished = :isFinished")
    fun getCartsAndProducts(isFinished : Boolean) : Flowable<List<CartsAndCartProducts>>


}