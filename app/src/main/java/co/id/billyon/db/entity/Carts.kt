package co.id.billyon.db.entity

import android.arch.persistence.room.*

@Entity(tableName = "carts",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = Products::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("product_id"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )
        ))
data class Carts(
        @ColumnInfo(name = "product_id")
        var productId : Long,
        @ColumnInfo(name = "store_id")
        var storeId : Int,
        @ColumnInfo(name = "quantity")
        var quantity : Int

) {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Long = 0
}