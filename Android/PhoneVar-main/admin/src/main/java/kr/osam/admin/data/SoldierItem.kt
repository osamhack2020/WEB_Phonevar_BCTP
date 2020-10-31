package kr.osam.admin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Soldier")
data class SoldierItem @JvmOverloads constructor(
    @ColumnInfo(name = "serviceNumber") var serviceNumber: String = "",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "organization") var organization: String = "",
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String = "",
    @ColumnInfo(name = "dischargeDate") var dischargeDate: String = "",
    @ColumnInfo(name = "unitId") var unitId: String = "",
    @ColumnInfo(name = "statusCode") var statusCode: String = "",
    @ColumnInfo(name = "deviceUUID") var deviceUUID: String = "",
    @ColumnInfo(name = "phoneNumber") var isDeleted: Boolean = false,
    @PrimaryKey
    @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()
)