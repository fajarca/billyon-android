package co.id.billyon.db.entity

import androidx.room.*
import co.id.billyon.util.Converters

@Entity(tableName = "products",
        indices = arrayOf(
                Index(value = "name", name = "idx_name")
        ),
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = Category::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("category_id"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )
        )
)
data class Products(

        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: Long,

        @ColumnInfo(name = "store_id")
        var storeId: Long,

        @ColumnInfo(name = "category_id")
        var categoryId: Long,

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

        var updated_date: String,

        @Ignore
        var showAddToCartButton : Boolean = true,
        @Ignore
        var showQuantityPicker : Boolean = false

) {
        constructor() : this(0, 0, 0, "", "",  0,  0,  0,  0,  false, false, "", "", true, false)
}

