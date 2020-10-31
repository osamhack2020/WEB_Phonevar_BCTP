package kr.osam.phonevar.data.source.log.local

import androidx.annotation.VisibleForTesting
import io.reactivex.Flowable
import kr.osam.phonevar.data.LogItem
import kr.osam.phonevar.data.source.log.LogDataSource
import kr.osam.phonevar.util.Schedulers.BaseSchedulerProvider
import java.text.SimpleDateFormat
import java.util.*

class LogLocalDatabase private constructor(
    val appExecutors: BaseSchedulerProvider,
    val logDao: LogDao
) : LogDataSource {
    override fun getLogList(): Flowable<List<LogItem>> =
        Flowable.fromCallable { logDao.getAllLog() }

    override fun getLogList(isSync: Boolean): Flowable<List<LogItem>> =
        Flowable.fromCallable { logDao.getSyncLog(isSync) }

    override fun saveLog(logItem: LogItem) {
        if (isLogPossible(logItem.loggedAt)) {
            Flowable.fromCallable {
                logDao.insertLog(logItem)
            }.subscribeOn(appExecutors.io())
                .subscribe {}
        }
    }

    private fun isLogPossible(loggedAt: String): Boolean {
        val format = SimpleDateFormat("HH:mm:ss")
        val logTime = loggedAt.split(" ")[1]
        val startTime = "18:00:00"
        val endTime = "21:00:00"
        val calendar: Calendar = GregorianCalendar(TimeZone.getTimeZone("Asia/Calcutta"))
        val startCalendar: Calendar = GregorianCalendar(TimeZone.getTimeZone("Asia/Calcutta"))
        val endCalendar: Calendar = GregorianCalendar(TimeZone.getTimeZone("Asia/Calcutta"))
        calendar.time = format.parse(logTime)
        startCalendar.time = format.parse(startTime)
        endCalendar.time = format.parse(endTime)
        return calendar.before(startCalendar) || calendar.after(endCalendar)
    }

    override fun updateLog(logItem: LogItem, isSync: Boolean) {
        logDao.updateLogIsSync(logItem.id!!, isSync)
    }

    override fun syncLog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllLog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshLog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveLog(logItem: List<LogItem>): Flowable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private var INSTANCE: LogLocalDatabase? = null

        @JvmStatic
        fun getInstance(appExecutors: BaseSchedulerProvider, logDao: LogDao): LogLocalDatabase {
            if (INSTANCE == null) {
                synchronized(LogLocalDatabase::javaClass) {
                    INSTANCE = LogLocalDatabase(appExecutors, logDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}