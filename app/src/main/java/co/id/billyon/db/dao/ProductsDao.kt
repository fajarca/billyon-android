package co.id.billyon.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import co.id.billyon.db.entity.Products

@Dao
interface ProductsDao {
    @Query("SELECT * FROM products")
    fun getAllProduct() : LiveData<List<Products>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Products) : Long

    @Delete
    fun delete(product: Products)

    @Query("DELETE FROM products")
    fun deleteAll()

}