package co.id.billyon.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded

data class CategoryWithProducts (
    @Embedded
    var category : Category,

    @ColumnInfo(name = "count")
    var productCount : Int

)
