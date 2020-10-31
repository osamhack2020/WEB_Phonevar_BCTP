package kr.osam.phonevar

import android.content.Context
import kr.osam.phonevar.data.source.log.LogRepository
import kr.osam.phonevar.data.source.log.local.LogDatabase
import kr.osam.phonevar.data.source.log.local.LogLocalDatabase
import kr.osam.phonevar.data.source.log.remote.LogRemoteDatabase
import kr.osam.phonevar.data.source.unit.UnitRepository
import kr.osam.phonevar.data.source.unit.local.UnitDatabase
import kr.osam.phonevar.data.source.unit.local.UnitLocalDataSource
import kr.osam.phonevar.data.source.unit.remote.UnitRemoteDataSource
import kr.osam.phonevar.data.source.user.UserRepository
import kr.osam.phonevar.data.source.user.local.UserDatabase
import kr.osam.phonevar.data.source.user.local.UserLocalDataSource
import kr.osam.phonevar.data.source.user.remote.UserRemoteDataSource
import kr.osam.phonevar.util.Schedulers.BaseSchedulerProvider
import kr.osam.phonevar.util.Schedulers.SchedulerProvider


object Injection {
    fun provideUnitRepository(context: Context): UnitRepository {
        val database = UnitDatabase.getInstance(context)
        return UnitRepository.getInstance(
            UnitRemoteDataSource,
            UnitLocalDataSource.getInstance(provideSchedulerProvider() , database.unitDao())
        )
    }

    fun provideUserRepository(context: Context): UserRepository {
        val database = UserDatabase.getInstance(context)
        return UserRepository.getInstance(
            UserRemoteDataSource,
            UserLocalDataSource.getInstance(provideSchedulerProvider() ,database.userDao())
        )
    }

    fun provideLogRepository(context: Context): LogRepository {
        val database = LogDatabase.getInstance(context)
        return LogRepository.getInstance(
            LogRemoteDatabase,
            LogLocalDatabase.getInstance(provideSchedulerProvider(), database.logDao())
        )
    }

    fun provideSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider
    }
}
