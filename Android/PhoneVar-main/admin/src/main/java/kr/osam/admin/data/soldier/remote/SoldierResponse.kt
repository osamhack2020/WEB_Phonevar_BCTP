package kr.osam.admin.data.soldier.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kr.osam.admin.data.SoldierItem

data class SoldierResponse @JvmOverloads constructor(
    @SerializedName("deviceUUID")
    @Expose
    val deviceUUID: String,
    @SerializedName("dischargeDate")
    @Expose
    val dischargeDate: String,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("isDeleted")
    @Expose
    val isDeleted: Boolean,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("organization")
    @Expose
    val organization: String,
    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String,
    @SerializedName("serviceNumber")
    @Expose
    val serviceNumber: String,
    @SerializedName("statusCode")
    @Expose
    val statusCode: Int,
    @SerializedName("token")
    @Expose
    val token: String,
    @SerializedName("unitId")
    @Expose
    val unitId: Int
)

fun SoldierResponse.convertToModel(): SoldierItem {
    return SoldierItem(
        this.serviceNumber,
        this.name,
        this.organization,
        this.phoneNumber,
        this.dischargeDate,
        this.unitId.toString(),
        this.statusCode.toString(),
        this.deviceUUID,
        this.isDeleted,
        this.id.toString()
    )
}