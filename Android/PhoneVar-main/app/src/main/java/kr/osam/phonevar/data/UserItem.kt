package kr.osam.phonevar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "User")
data class UserItem constructor(
    @ColumnInfo(name = "deviceUUID") var deviceUUID : String = "",
    @ColumnInfo(name = "dischargeDate") var dischargeDate: String = "",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "organization") var organization: String = "",
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String = "",
    @ColumnInfo(name = "serviceNumber") var serviceNumber: String = "",
    @ColumnInfo(name = "unitId") var unitId: Int = 0,
    @ColumnInfo(name = "statusCode") var statusCode: Int = 0,
    @ColumnInfo(name = "token") var token: String = "",
    @ColumnInfo(name = "isDeleted") var isDeleted : Boolean = false,
    @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()
)