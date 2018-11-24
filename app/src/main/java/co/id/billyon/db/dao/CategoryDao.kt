package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.CategoryWithProducts
import io.reactivex.Flowable

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAllCategory(): Flowable<List<Category>>

    @Query("select a.id, a.category_name,a.image_path,a.store_id, count(b.name) as count from category a left join products b on a.id = b.category_id group by a.id, a.category_name,a.store_id")
    fun getAllCategoryWithProductCount(): Flowable<List<CategoryWithProducts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<Category>)

    @Query("DELETE FROM category where id = :categoryId")
    fun delete(categoryId: Long)

    @Query("DELETE FROM category")
    fun deleteAll()

    @Update
    fun update(category: Category)

}