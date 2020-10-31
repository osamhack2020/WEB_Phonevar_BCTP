package kr.osam.admin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.osam.admin.util.DateUtil


@Entity(tableName = "Log")
data class LogItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "logType")
    var logType: Int = 0,
    @ColumnInfo(name = "loggedAt")
    var loggedAt: String = DateUtil.getCurrentDate(DateUtil.LOG_DATE_TYPE),
    @ColumnInfo(name = "createdAt")
    var createdAt: String = DateUtil.getCurrentDate(DateUtil.LOG_DATE_TYPE)
)

enum class LogState (val type : Int){
    BOOT(0),
    START_SERVICE(1),
    SHUT_DOWN(2);

    companion object{
        fun getLogState(type : Int) = when(type){
            0 -> BOOT
            1 -> START_SERVICE
            2 -> SHUT_DOWN
            else -> BOOT
        }
    }
}