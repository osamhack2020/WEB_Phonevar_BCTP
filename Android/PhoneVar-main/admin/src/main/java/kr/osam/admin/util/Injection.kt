package kr.osam.admin.util

import android.content.Context
import kr.osam.admin.data.log.remote.LogRemoteDatabase
import kr.osam.admin.data.soldier.SoldierRepository
import kr.osam.admin.data.soldier.remote.SoldierRemoteDataSource
import kr.osam.admin.data.unit.local.UnitDatabase
import kr.osam.admin.data.unit.local.UnitLocalDataSource
import kr.osam.admin.data.unit.remote.UnitRemoteDataSource
import kr.osam.phonevar.data.source.log.LogRepository
import kr.osam.phonevar.data.source.unit.UnitRepository
import kr.osam.phonevar.util.Schedulers.BaseSchedulerProvider
import kr.osam.phonevar.util.Schedulers.SchedulerProvider


object Injection {
    fun provideSoldierRepository(context: Context): SoldierRepository {
        return SoldierRepository.getInstance(
            SoldierRemoteDataSource
        )
    }

    fun provideUnitRepository(context: Context): UnitRepository {
        val database = UnitDatabase.getInstance(context)
        return UnitRepository.getInstance(
            UnitRemoteDataSource,
            UnitLocalDataSource.getInstance(provideSchedulerProvider(), database.unitDao())
        )
    }

    fun provideLogRepository(context: Context): LogRepository {
        return LogRepository.getInstance(
            LogRemoteDatabase
        )
    }

    fun provideSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider
    }
}
