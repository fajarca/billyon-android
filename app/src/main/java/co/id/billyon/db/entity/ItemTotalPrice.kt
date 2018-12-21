package co.id.billyon.db.entity

import androidx.room.ColumnInfo

data class ItemTotalPrice(
        @ColumnInfo(name = "item_count")
        var itemCount : Int,
        @ColumnInfo(name = "total_price")
        var totalPrice : Long
)