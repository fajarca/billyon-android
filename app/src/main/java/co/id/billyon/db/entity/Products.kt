package co.id.billyon.db.entity

import android.arch.persistence.room.*
import co.id.billyon.util.Converters
import java.util.*

@Entity(tableName = "products",indices = arrayOf(Index(value = "name",name = "idx_name")))
data class Products(

        @PrimaryKey
        @ColumnInfo(name = "product_id")
        var id: Long,

        @ColumnInfo(name = "store_id")
        var storeId: Int,

        @ColumnInfo(name = "category_id")
        var categoryId: Int,

        @ColumnInfo(name = "image_path")
        var imagePath: String,

        var name: String,
        var stock: Int,

        @ColumnInfo(name = "min_stock")
        var minStock: Int,

        @ColumnInfo(name = "display_price")
        var displayPrice: Long,

        @ColumnInfo(name = "actual_price")
        var actualPrice: Long, //harga jual

        @TypeConverters(Converters::class)
        @ColumnInfo(name = "is_active")
        var isActive: Boolean,

        @TypeConverters(Converters::class)
        @ColumnInfo(name = "need_synced")
        var need_synced: Boolean,

        var created_date: String,

        var updated_date: String


)

