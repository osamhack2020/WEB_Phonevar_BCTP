package kr.osam.admin.data.log.remote

import io.reactivex.Flowable
import kr.osam.admin.data.LogItem
import kr.osam.admin.data.LogState
import kr.osam.admin.data.log.LogDataSource
import kr.osam.admin.util.RetrofitManager


object LogRemoteDatabase : LogDataSource {
    override fun getLogList(userID: Int): Flowable<List<LogItem>> = RetrofitManager.getInstance()
        .retrofit
        .create(LogService::class.java)
        .getLogs(userID)
        .flatMap { response ->
            Flowable.fromIterable(response.userLogs)
                .map { item -> item.convertToModel() }
                .toList()
                .toFlowable()
        }


    override fun getLogList(userID: Int, logType: List<LogState>): Flowable<List<LogItem>>  =
        Flowable.empty()


    override fun refreshLog() {
        // doing nothing
    }
}
