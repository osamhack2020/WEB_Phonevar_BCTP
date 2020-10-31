package kr.osam.phonevar.data.source.unit.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.osam.phonevar.data.UnitItem

@Database(entities = [UnitItem::class], version = 1)
abstract class UnitDatabase : RoomDatabase() {
    abstract fun unitDao(): UnitDao

    companion object {
        const val DB_NAME = "Unit.db"
        private var INSTANCE: UnitDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): UnitDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UnitDatabase::class.java, DB_NAME
                    ).build()
                }
                return INSTANCE!!
            }
        }
    }
}