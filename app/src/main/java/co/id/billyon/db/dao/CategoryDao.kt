package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.Products
import io.reactivex.Flowable

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAllCategory() : Flowable<List<Category>>

 /*   @Query("SELECT * FROM category where category_id = :categoryId")
    fun findCategory(categoryId : Long)*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<Category>)

    @Delete
    fun delete(category: Category)

    @Query("DELETE FROM category")
    fun deleteAll()

    @Update
    fun update(category: Category)

}