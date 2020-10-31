package kr.osam.phonevar.data.source.user.remote

import com.google.gson.JsonObject
import io.reactivex.Flowable
import kr.osam.phonevar.data.UserItem
import kr.osam.phonevar.data.source.user.UserDataSource
import kr.osam.phonevar.util.RetrofitManager
import org.json.JSONObject

object UserRemoteDataSource : UserDataSource {

    override fun getUser(): Flowable<List<UserItem>> = Flowable.empty()

    override fun getUser(userItem: UserItem): Flowable<List<UserItem>> {
        var body: JsonObject = JsonObject().apply {
            addProperty("deviceUUID", userItem.deviceUUID)
            addProperty("name", userItem.name)
            addProperty("serviceNumber", userItem.serviceNumber)
        }
        return RetrofitManager.getInstance()
            .retrofit
            .create(UserService::class.java)
            .saveUser(body)
            .map { it.convertToModel() }
    }

    override fun saveUser(userItem: UserItem) {
        // doing nothing
    }

    override fun deleteUser() {
        // doing nothing
    }

    override fun refreshUser() {
        // doing nothing
    }
}