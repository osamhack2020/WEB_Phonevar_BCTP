package kr.osam.phonevar.data.source.log.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.osam.phonevar.data.LogItem

@Database(entities = [LogItem::class], version = 1)
abstract class LogDatabase : RoomDatabase() {
    abstract fun logDao(): LogDao
    companion object {
        const val DB_NAME = "Log.db"
        private var INSTANCE: LogDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): LogDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LogDatabase::class.java,
                        DB_NAME
                    ).build()
                }
                return INSTANCE!!
            }
        }
    }
}