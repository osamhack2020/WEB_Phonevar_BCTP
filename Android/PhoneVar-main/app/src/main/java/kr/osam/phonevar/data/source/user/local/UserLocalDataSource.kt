package kr.osam.phonevar.data.source.user.local

import androidx.annotation.VisibleForTesting
import io.reactivex.Flowable
import kr.osam.phonevar.data.UserItem
import kr.osam.phonevar.data.source.user.UserDataSource
import kr.osam.phonevar.util.Schedulers.BaseSchedulerProvider
import kr.osam.phonevar.util.TokenSharedPreference

class UserLocalDataSource private constructor(
    val appExecutors: BaseSchedulerProvider,
    val userDao: UserDao
) : UserDataSource {
    override fun getUser(): Flowable<List<UserItem>> = Flowable.fromCallable { userDao.getUser() }

    override fun getUser(userItem: UserItem): Flowable<List<UserItem>> {
        return getUser()
    }

    override fun saveUser(userItem: UserItem) {
        userDao.insertUser(userItem)
        TokenSharedPreference.getInstance().saveToken(userItem.token)
        TokenSharedPreference.getInstance().saveID(userItem.id.toInt())
    }

    override fun deleteUser() {
        userDao.deleteAllUser()
    }

    override fun refreshUser() {
        // doing nothing
    }

    companion object {
        private var INSTANCE: UserLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: BaseSchedulerProvider,userDao: UserDao): UserLocalDataSource {
            if (INSTANCE == null) {
                synchronized(UserLocalDataSource::javaClass) {
                    INSTANCE = UserLocalDataSource(appExecutors,userDao)
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