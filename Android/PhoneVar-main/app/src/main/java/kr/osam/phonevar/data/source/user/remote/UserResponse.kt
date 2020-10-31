package kr.osam.phonevar.data.source.user.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kr.osam.phonevar.data.UserItem

data class UserResponse(
    @SerializedName("deviceUUID") @Expose var deviceUUID: String = "",
    @SerializedName("dischargeDate") @Expose var dischargeDate: String = "",
    @SerializedName("name") @Expose var name: String = "",
    @SerializedName("organization") @Expose var organization: String = "",
    @SerializedName("phoneNumber") @Expose var phoneNumber: String = "",
    @SerializedName("serviceNumber") @Expose var serviceNumber: String = "",
    @SerializedName("unitId") @Expose var unitId: Int = 0,
    @SerializedName("statusCode") @Expose var statusCode: Int = 0,
    @SerializedName("token") @Expose var token: String = "",
    @SerializedName("isDeleted") @Expose var isDeleted: Boolean = false,
    @SerializedName("id") @Expose var id: Int
)

fun UserResponse.convertToModel(): List<UserItem> {
    var userList = mutableListOf<UserItem>()
    userList.add(
        UserItem(
            this.deviceUUID,
            this.dischargeDate,
            this.name,
            this.organization,
            this.phoneNumber,
            this.serviceNumber,
            this.unitId,
            this.statusCode,
            this.token,
            this.isDeleted,
            this.id.toString()
        )
    )
    return userList
}