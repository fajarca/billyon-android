package co.id.billyon.db.entity

import android.arch.persistence.room.*
import co.id.billyon.util.Converters

@Entity(tableName = "cart_products",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = Products::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("product_id"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                ),
                ForeignKey(
                        entity = Carts::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("carts_id"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )
        ))
data class CartProducts(
        @ColumnInfo(name = "product_id")
        var productId : Long,
        @ColumnInfo(name = "store_id")
        var storeId : Int,
        @ColumnInfo(name = "carts_id")
        var cashierId : Int,
        @ColumnInfo(name = "quantity")
        var quantity : Int,
        @TypeConverters(Converters::class)
        @ColumnInfo(name = "need_synced")
        var need_synced: Boolean,
        @ColumnInfo(name = "inserted_date")
        var insertedDate: String


) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}