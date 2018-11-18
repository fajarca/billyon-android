package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.Category
import io.reactivex.Maybe

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAllCategory() : Maybe<List<Category>>

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