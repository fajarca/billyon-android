package co.id.billyon.db.dao.join

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import co.id.billyon.db.entity.join.CategoryAndProducts
import io.reactivex.Flowable

@Dao
interface CategoryAndProductsDao {
    @Query("SELECT * from category")
    fun getCategoryAndProducts() : Flowable<List<CategoryAndProducts>>
}