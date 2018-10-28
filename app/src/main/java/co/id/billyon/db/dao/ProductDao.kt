package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAllProduct() : List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM product")
    fun deleteAll()

}