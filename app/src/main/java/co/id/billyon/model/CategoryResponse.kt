package co.id.billyon.model

import com.google.gson.annotations.SerializedName


data class CategoryResponse(
        @SerializedName("error") val error: Boolean,
        @SerializedName("message") val message: String,
        @SerializedName("data") val data: List<Data>
) {

    data class Data(
            @SerializedName("created_dt") val createdDt: String,
            @SerializedName("updated_dt") val updatedDt: String,
            @SerializedName("is_active") val isActive: Int,
            @SerializedName("id") val id: Long,
            @SerializedName("category_name") val categoryName: String,
            @SerializedName("image_path") val imagePath: String,
            @SerializedName("store_id") val storeId: Int,
            @SerializedName("is_error") val isError: Boolean
    )
}