package kr.osam.phonevar.data.source.log.remote

import com.google.gson.JsonObject
import io.reactivex.Flowable
import kr.osam.phonevar.data.source.user.remote.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LogService {
    @POST("/phonevar/api/user/log")
    fun saveLog(@Body body: JsonObject): Flowable<LogResponse>
}