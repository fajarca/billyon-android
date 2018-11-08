package co.id.billyon.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "products")
data class Products(

        @PrimaryKey
        @ColumnInfo(name = "product_id")
        var id : Long,

        @ColumnInfo(name = "store_id")
        var storeId : Int,

        @ColumnInfo(name = "category_id")
        var categoryId : Int,

        @ColumnInfo(name = "image_path")
        var imagePath : String,

        var name : String,
        var stock : Int,
        var minStock : Int,
        var displayPrice : Long,
        var actualPrice : Long, //harga jual
        var dozenPrice : Long,
        var isActive : Int


)

