package co.id.billyon.db.entity.join

import androidx.room.ColumnInfo


data class ProductsAndCartProduct(

        @ColumnInfo(name = "product_id")
        var productId : Long,

        @ColumnInfo(name = "category_id")
        var categoryId : Long,

        @ColumnInfo(name = "store_id")
        var storeId : Long,

        @ColumnInfo(name = "carts_id")
        var cartId : Long,

        @ColumnInfo(name = "name")
        var productName : String,

        @ColumnInfo(name = "stock")
        var stock : Int,

        @ColumnInfo(name = "min_stock")
        var minStock : Int,

        @ColumnInfo(name = "quantity")
        var quantity : Int,

        @ColumnInfo(name = "display_price")
        var displayPrice : Long


)