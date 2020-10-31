package kr.osam.phonevar.data.source.user.remote

import com.google.gson.JsonObject
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {
    @POST("/phonevar/api/user/register")
    fun saveUser(@Body body: JsonObject): Flowable<UserResponse>
}