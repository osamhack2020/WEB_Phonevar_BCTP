package kr.osam.phonevar.data.source.log

import io.reactivex.Flowable
import kr.osam.phonevar.data.LogItem

interface LogDataSource {
    fun getLogList(): Flowable<List<LogItem>>

    fun getLogList(isSync: Boolean): Flowable<List<LogItem>>

    fun saveLog(logItem: LogItem)

    fun saveLog(logItem: List<LogItem>): Flowable<Boolean>

    fun updateLog(logItem: LogItem, isSync: Boolean)

    fun syncLog()

    fun deleteAllLog()

    fun refreshLog()
}