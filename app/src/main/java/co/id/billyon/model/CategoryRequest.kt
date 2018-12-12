package co.id.billyon.model

import com.google.gson.annotations.SerializedName


data class CategoryRequest(
        @SerializedName("category_name") val categoryName: String,
        @SerializedName("image_path") val imagePath: String,
        @SerializedName("store_id") val storeId: Int
)