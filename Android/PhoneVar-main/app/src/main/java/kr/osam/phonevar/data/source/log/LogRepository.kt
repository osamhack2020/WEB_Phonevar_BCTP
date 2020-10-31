package kr.osam.phonevar.data.source.log

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.VisibleForTesting
import io.reactivex.Flowable
import kr.osam.phonevar.data.LogItem


class LogRepository(
    val logRemoteDataSource: LogDataSource,
    val logLocalDataSource: LogDataSource
) : LogDataSource {

    @VisibleForTesting
    var cachedUnit: LinkedHashMap<Int, LogItem> = LinkedHashMap()
    @VisibleForTesting
    var cacheIsDirty = false

    override fun getLogList(): Flowable<List<LogItem>> {
        if (!cacheIsDirty)
            return Flowable.fromIterable(cachedUnit.values).toList().toFlowable()

        return getAndCacheLocalLog()
    }

    override fun getLogList(isSync: Boolean): Flowable<List<LogItem>> {
        if (!cacheIsDirty)
            return Flowable.fromIterable(cachedUnit.values)
                .filter { logItem: LogItem -> logItem.isSync == isSync }.toList().toFlowable()

        return getAndCacheLocalLog().flatMap { listUser ->
            Flowable.fromIterable(listUser).filter { log -> log.isSync == isSync }
        }.toList().toFlowable()
    }

    override fun saveLog(logItem: LogItem) {
        logLocalDataSource.saveLog(logItem)
    }

    override fun saveLog(logItem: List<LogItem>): Flowable<Boolean> = Flowable.empty()


    private fun getAndCacheLocalLog(): Flowable<List<LogItem>> =
        logLocalDataSource.getLogList()
            .flatMap {
                Flowable.fromIterable(it)
            }.doOnNext { t: LogItem ->
                cachedUnit[t.id!!.toInt()] = t
            }.toList()
            .toFlowable()

    @SuppressLint("CheckResult")
    override fun syncLog() {
        logLocalDataSource.getLogList(false)
            .map { logList: List<LogItem> ->
                Log.d("Local", logList.size.toString())
                if (!logList.isNullOrEmpty())
                    logRemoteDataSource.saveLog(logList).subscribe { isSuccess ->
                        run {
                            Log.d("Local", isSuccess.toString())
                            if (isSuccess) {
                                Flowable.fromIterable(logList).subscribe { log ->
                                    log.isSync = true
                                    logLocalDataSource.updateLog(log, true)
                                    cachedUnit.put(log.id!!, log)
                                    Log.d("Local", log.id.toString())
                                }
                            }
                        }
                    }
            }.subscribe { }

    }

    override fun deleteAllLog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateLog(logItem: LogItem, isSync: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshLog() {
        cacheIsDirty = true
    }

    companion object {
        private var INSTANCE: LogRepository? = null

        @JvmStatic
        fun getInstance(
            logRemoteDataSource: LogDataSource,
            logLocalDataSource: LogDataSource
        ): LogRepository {
            return INSTANCE ?: LogRepository(logRemoteDataSource, logLocalDataSource)
                .apply { INSTANCE = this }
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}