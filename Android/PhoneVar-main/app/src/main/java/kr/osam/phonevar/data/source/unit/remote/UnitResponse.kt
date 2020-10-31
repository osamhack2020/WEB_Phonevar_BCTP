package kr.osam.phonevar.data.source.unit.local

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kr.osam.phonevar.data.UnitItem

data class UnitResponse @JvmOverloads constructor(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("unitCode") @Expose val unitCode: Int,
    @SerializedName("unitName") @Expose val unitName: String,
    @SerializedName("isDeleted") @Expose val isDeleted: Boolean
)

fun UnitResponse.convertToModel(): UnitItem {
    return UnitItem(this.unitName, this.unitCode.toString(), this.isDeleted, this.id.toString())
}