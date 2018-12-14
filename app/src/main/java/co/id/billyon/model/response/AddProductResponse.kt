package co.id.billyon.model.response

import com.google.gson.annotations.SerializedName


data class AddProductResponse(
        @SerializedName("error") val error: Boolean,
        @SerializedName("message") val message: String,
        @SerializedName("data") val data: List<Data>
) {

    data class Data(
            @SerializedName("created_dt") val createdDt: String,
            @SerializedName("updated_dt") val updatedDt: String,
            @SerializedName("is_active") val isActive: Int,
            @SerializedName("id") val id: Long,
            @SerializedName("image_path") val imagePath: String,
            @SerializedName("name") val name: String,
            @SerializedName("stock") val stock: Int,
            @SerializedName("min_stock") val minStock: Int,
            @SerializedName("display_price") val displayPrice: Int,
            @SerializedName("actual_price") val actualPrice: Int,
            @SerializedName("store_id") val storeId: Int,
            @SerializedName("category_id") val categoryId: Long
    )
}