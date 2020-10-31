package kr.osam.phonevar.data.source.user.local

import androidx.room.*
import kr.osam.phonevar.data.UserItem

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getUser(): List<UserItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userItem: UserItem)

    @Update
    fun updateUser(userItem: UserItem): Int

    @Query("DELETE FROM User")
    fun deleteAllUser()
}