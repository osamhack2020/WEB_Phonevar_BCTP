package kr.osam.phonevar.data.source.user.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.osam.phonevar.data.UnitItem
import kr.osam.phonevar.data.UserItem

@Database(entities = [UserItem::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DB_NAME = "User.db"
        private var INSTANCE: UserDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): UserDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, DB_NAME
                    ).build()
                }
                return INSTANCE!!
            }
        }
    }
}