package kr.osam.phonevar.data.source.log.local

import androidx.room.*
import kr.osam.phonevar.data.LogItem

@Dao
interface LogDao {
    @Query("SELECT * FROM Log ORDER BY id ASC")
    fun getAllLog(): List<LogItem>

    @Query("SELECT * FROM Log WHERE isSync = :isSync ORDER BY id ASC")
    fun getSyncLog(isSync: Boolean): List<LogItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLog(logItem: LogItem)

    @Update
    fun updateLog(logItem: LogItem): Int

    @Query("UPDATE Log SET isSync = :isSync WHERE id = :logId")
    fun updateLogIsSync(logId: Int, isSync: Boolean)

    @Query("DELETE FROM Log")
    fun deleteAllLog()
}