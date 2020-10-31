package kr.osam.phonevar.data.source.unit.local

import androidx.annotation.VisibleForTesting
import io.reactivex.Flowable
import kr.osam.phonevar.data.UnitItem
import kr.osam.phonevar.data.source.unit.UnitDataSource
import kr.osam.phonevar.util.Schedulers.BaseSchedulerProvider


class UnitLocalDataSource private constructor(
    val appExecutors: BaseSchedulerProvider,
    val unitDao: UnitDao
) : UnitDataSource {
    override fun getUnits(): Flowable<List<UnitItem>> = Flowable.fromCallable { unitDao.getUnits() }

    override fun saveUnit(unitItem: UnitItem) {
        unitDao.insertUnit(unitItem)
    }

    override fun refreshUnits() {
        // Not required
    }

    companion object {
        private var INSTANCE: UnitLocalDataSource? = null

        @JvmStatic
        fun getInstance(
            appExecutors: BaseSchedulerProvider,
            tasksDao: UnitDao
        ): UnitLocalDataSource {
            if (INSTANCE == null) {
                synchronized(UnitLocalDataSource::javaClass) {
                    INSTANCE = UnitLocalDataSource(appExecutors, tasksDao)
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