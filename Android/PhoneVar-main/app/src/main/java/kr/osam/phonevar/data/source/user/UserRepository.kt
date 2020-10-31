package kr.osam.phonevar.data.source.user

import androidx.annotation.VisibleForTesting
import io.reactivex.Flowable
import kr.osam.phonevar.data.UserItem

class UserRepository(
    private val userRemoteDataSource: UserDataSource,
    private val userLocalDataSource: UserDataSource
) : UserDataSource {

    @VisibleForTesting
    var cachedUnit: LinkedHashMap<Int, UserItem> = LinkedHashMap()
    @VisibleForTesting
    var cacheIsDirty = false

    override fun getUser(): Flowable<List<UserItem>> {
        if (!cacheIsDirty)
            return Flowable.fromIterable(cachedUnit.values).toList().toFlowable()

        return getAndCacheLocalUser()
    }


    override fun getUser(userItem: UserItem): Flowable<List<UserItem>> {
        return getAndSaveRemoteUser(userItem)
    }

    override fun saveUser(userItem: UserItem) {
        // doing nothing
    }

    override fun deleteUser() {
        userLocalDataSource.deleteUser()
    }

    override fun refreshUser() {
        cacheIsDirty = true
    }

    private fun getAndCacheLocalUser(): Flowable<List<UserItem>> =
        userLocalDataSource.getUser()
            .flatMap {
                Flowable.fromIterable(it)
            }.doOnNext { t: UserItem ->
                cachedUnit[t.id.toInt()] = t
            }.toList()
            .toFlowable()

    private fun getAndSaveRemoteUser(userItem: UserItem): Flowable<List<UserItem>> =
        userRemoteDataSource
            .getUser(userItem)
            .flatMap { users ->
                Flowable.fromIterable(users).doOnNext { user ->
                    run {
                        userLocalDataSource.deleteUser()
                        userLocalDataSource.saveUser(user)
                        cachedUnit.put(user.id.toInt(), user)
                    }
                }
            }.toList().toFlowable().doOnComplete {
                cacheIsDirty = false
            }

    companion object {
        private var INSTANCE: UserRepository? = null

        @JvmStatic
        fun getInstance(
            userRemoteDataSource: UserDataSource,
            userLocalDataSource: UserDataSource
        ): UserRepository {
            return INSTANCE ?: UserRepository(userRemoteDataSource, userLocalDataSource)
                .apply { INSTANCE = this }
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}