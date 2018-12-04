package co.id.billyon.db.dao.join

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import co.id.billyon.db.entity.ItemTotalPrice
import co.id.billyon.db.entity.join.CartsAndCartProducts
import io.reactivex.Flowable

@Dao
interface CartsAndCartProductsDao {
    @Query("SELECT * from carts where is_finished = :isFinished")
    fun getCartsAndProducts(isFinished : Boolean) : Flowable<List<CartsAndCartProducts>>

    @Query("SELECT SUM(b.quantity) AS item_count, SUM(a.display_price * b.quantity) AS total_price FROM products a LEFT JOIN cart_products b ON a.id = b.product_id")
    fun getItemCountAndTotalPrice() : Flowable<ItemTotalPrice>

}