package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.Products
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products")
    fun getAll() : Flowable<List<Products>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Products)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Products>)

    @Delete
    fun delete(product: Products)

    @Query("DELETE FROM products")
    fun deleteAll()

    @Update
    fun update(product: Products)

}