package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.Products
import io.reactivex.Flowable

@Dao
interface ProductsDao {
    @Query("SELECT * FROM products")
    fun getAllProduct() : Flowable<List<Products>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Products) : Long

    @Delete
    fun delete(product: Products)

    @Query("DELETE FROM products")
    fun deleteAll()

}