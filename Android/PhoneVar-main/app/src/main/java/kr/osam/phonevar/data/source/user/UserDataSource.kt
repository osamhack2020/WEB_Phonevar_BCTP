package kr.osam.phonevar.data.source.user

import io.reactivex.Flowable
import kr.osam.phonevar.data.UserItem

interface UserDataSource {
    fun getUser(): Flowable<List<UserItem>>

    fun getUser(userItem: UserItem): Flowable<List<UserItem>>

    fun saveUser(userItem: UserItem)

    fun deleteUser()

    fun refreshUser()
}