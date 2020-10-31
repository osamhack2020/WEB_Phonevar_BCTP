package kr.osam.phonevar.data.source.log.remote

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Flowable
import kr.osam.phonevar.data.LogItem
import kr.osam.phonevar.data.source.log.LogDataSource
import kr.osam.phonevar.util.RetrofitManager
import kr.osam.phonevar.util.TokenSharedPreference

object LogRemoteDatabase : LogDataSource {
    override fun getLogList(): Flowable<List<LogItem>> = Flowable.empty()

    override fun getLogList(isSync: Boolean): Flowable<List<LogItem>> = Flowable.empty()

    override fun saveLog(logItem: LogItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveLog(logItem: List<LogItem>): Flowable<Boolean> {
        var logs: JsonArray = JsonArray().apply {
            for (log in logItem) {
                add(JsonObject().apply {
                    addProperty("logType", log.logType)
                    addProperty("loggedAt", log.loggedAt)
                })
            }
        }
        var body: JsonObject = JsonObject().apply {
            addProperty("userId", TokenSharedPreference.getInstance().id)
            add("userLogs", logs)
        }
        return RetrofitManager.getInstance().retrofit.create(LogService::class.java).saveLog(body)
            .onErrorReturn { LogResponse(false) }
            .map { item -> item.isSuccess }
    }

    override fun updateLog(logItem: LogItem, isSync: Boolean) {
        // doing nothing
    }

    override fun syncLog() {
        // doing nothing
    }

    override fun deleteAllLog() {
        // doing nothing
    }

    override fun refreshLog() {
        // doing nothing
    }
}