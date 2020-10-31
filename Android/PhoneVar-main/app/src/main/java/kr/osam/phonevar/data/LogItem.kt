package kr.osam.phonevar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.osam.phonevar.util.DateUtil

@Entity(tableName = "Log")
data class LogItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "logType")
    var logType: Int = 0,
    @ColumnInfo(name = "loggedAt")
    var loggedAt: String = DateUtil.getCurrentDate(DateUtil.LOG_DATE_TYPE),
    @ColumnInfo(name = "isSync")
    var isSync: Boolean = false
)