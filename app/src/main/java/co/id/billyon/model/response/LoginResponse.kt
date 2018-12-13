package co.id.billyon.model.response

import com.google.gson.annotations.SerializedName


data class LoginResponse(
        @SerializedName("error") val error: Boolean,
        @SerializedName("message") val message: String,
        @SerializedName("data") val data: Data
) {

    data class Data(
            @SerializedName("users") val users: Users,
            @SerializedName("menus") val menus: List<Any>,
            @SerializedName("stores") val stores: List<Any>
    ) {

        data class Users(
                @SerializedName("created_dt") val createdDt: String,
                @SerializedName("updated_dt") val updatedDt: String,
                @SerializedName("is_active") val isActive: Int,
                @SerializedName("id") val id: Int,
                @SerializedName("email") val email: String,
                @SerializedName("first_name") val firstName: String,
                @SerializedName("last_name") val lastName: String,
                @SerializedName("phone") val phone: String,
                @SerializedName("address") val address: String,
                @SerializedName("role_id") val roleId: Int
        )
    }
}