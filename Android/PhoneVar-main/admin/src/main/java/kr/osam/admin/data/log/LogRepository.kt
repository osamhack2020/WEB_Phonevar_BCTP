package kr.osam.phonevar.data.source.log

import androidx.annotation.VisibleForTesting
import io.reactivex.Flowable
import kr.osam.admin.data.LogItem
import kr.osam.admin.data.LogState
import kr.osam.admin.data.log.LogDataSource


class LogRepository(
    val logRemoteDataSource: LogDataSource
) : LogDataSource {

    @VisibleForTesting
    var cachedUnit: LinkedHashMap<String, LogItem> = LinkedHashMap()
    @VisibleForTesting
    var cacheIsDirty = false


    override fun getLogList(userID: Int): Flowable<List<LogItem>> {
        if (!cacheIsDirty)
            return Flowable.fromIterable(cachedUnit.values).toList().toFlowable()

        return getRemoteAndCacheLog(userID)
    }


    override fun getLogList(userID: Int, logType: List<LogState>): Flowable<List<LogItem>> {
        if (!cacheIsDirty)
            return Flowable.fromIterable(cachedUnit.values)
                .filter { logItem: LogItem -> logType.contains(LogState.getLogState(logItem.logType)) }
                .toList().toFlowable()

        return getRemoteAndCacheLog(userID).flatMap { response ->
            Flowable.fromIterable(response)
                .filter { item: LogItem -> logType.contains(LogState.getLogState(item.logType)) }
                .toList()
                .toFlowable()
        }
    }


    private fun getRemoteAndCacheLog(id: Int): Flowable<List<LogItem>> =
        logRemoteDataSource
            .getLogList(id)
            .flatMap { logs ->
                Flowable.fromIterable(logs).doOnNext { log ->
                    run {
                        cachedUnit.put(log.loggedAt, log)
                    }
                }
            }.toList().toFlowable().doOnComplete {
                cacheIsDirty = false
            }

    override fun refreshLog() {
        cacheIsDirty = true
        cachedUnit.clear()
    }

    companion object {
        private var INSTANCE: LogRepository? = null

        @JvmStatic
        fun getInstance(
            logRemoteDataSource: LogDataSource
        ): LogRepository {
            return INSTANCE ?: LogRepository(logRemoteDataSource)
                .apply { INSTANCE = this }
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}