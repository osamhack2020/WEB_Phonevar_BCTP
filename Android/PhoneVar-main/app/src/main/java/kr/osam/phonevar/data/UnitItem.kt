package kr.osam.phonevar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Units")
data class UnitItem @JvmOverloads constructor(
    @ColumnInfo(name = "unitName") var unitName : String = "",
    @ColumnInfo(name = "unitCode") var unitCode: String = "",
    @ColumnInfo(name = "isDeleted") var isDeleted : Boolean = false,
    @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()
)