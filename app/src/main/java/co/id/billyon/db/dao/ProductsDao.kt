package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.Products
import io.reactivex.Flowable

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products")
    fun getAll() : Flowable<List<Products>>

    @Query("select * from products where category_id  = :categoryId")
    fun getProductByCategoryId(categoryId : Int) : Flowable<List<Products>>

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