package kr.osam.admin.data.log

import io.reactivex.Flowable
import kr.osam.admin.data.LogItem
import kr.osam.admin.data.LogState

interface LogDataSource {
    fun getLogList(userID : Int): Flowable<List<LogItem>>

    fun getLogList(userID : Int , logType : List<LogState>): Flowable<List<LogItem>>

    fun refreshLog()
}