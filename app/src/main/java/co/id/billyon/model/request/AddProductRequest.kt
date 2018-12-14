package co.id.billyon.model.request

import com.google.gson.annotations.SerializedName


data class AddProductRequest(
        @SerializedName("store_id") val storeId: Long,
        @SerializedName("category_id") val categoryId: Long,
        @SerializedName("image_path") val imagePath: String,
        @SerializedName("name") val name: String,
        @SerializedName("stock") val stock: Int,
        @SerializedName("min_stock") val minStock: Int,
        @SerializedName("display_price") val displayPrice: Long,
        @SerializedName("actual_price") val actualPrice: Long
)