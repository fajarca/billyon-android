package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.CategoryWithProducts
import io.reactivex.Maybe

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAllCategory() : Maybe<List<Category>>

    /*@Query("select a.category_id, a.category_name, count(b.name) as product_count from category a left join products b on a.category_id = b.category_id group by a.category_id, a.category_name")
    fun getAllCategoryWithProductCount() : Maybe<List<CategoryWithProducts>>*/

    @Query("select a.*, count(b.name) as count from category a left join products b on a.category_id = b.category_id")
    fun getAllCategoryWithProductCount() : Maybe<List<CategoryWithProducts>>

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