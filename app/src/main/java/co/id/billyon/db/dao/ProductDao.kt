package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.Products

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProduct() : List<Products>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Products)

    @Delete
    fun delete(product: Products)

    @Query("DELETE FROM products")
    fun deleteAll()

}