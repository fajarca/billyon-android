package co.id.billyon.db.entity

import android.arch.persistence.room.ColumnInfo

data class CategoryWithProducts(

        @ColumnInfo(name = "category_id")
        var id: Long = 0,

        @ColumnInfo(name = "category_name")
        var categoryName: String,

        @ColumnInfo(name = "image_path")
        var imagePath : String,

        @ColumnInfo(name = "store_id")
        var storeId: Long,

        @ColumnInfo(name = "count")
        var productCount: Int

)
