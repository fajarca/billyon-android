package co.id.billyon.db.dao

import android.arch.persistence.room.*
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.Products
import co.id.billyon.db.entity.join.ProductsAndCartProduct
import io.reactivex.Flowable

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products")
    fun getAll() : Flowable<List<Products>>

    @Query("SELECT * FROM products where need_synced = '1'")
    fun getAllUnsyncronizedProduct() : Flowable<List<Products>>

    @Query("select a.id as product_id, a.category_id, a.store_id, a.name, a.stock, a.min_stock, b.quantity, a.display_price, b.carts_id from products a left join cart_products b on a.id = b.product_id where a.category_id = :categoryId")
    fun getProductByCategoryId(categoryId : Int) : Flowable<List<ProductsAndCartProduct>>

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