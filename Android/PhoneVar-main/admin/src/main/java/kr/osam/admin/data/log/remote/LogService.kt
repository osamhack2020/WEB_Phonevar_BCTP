package kr.osam.admin.data.log.remote

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface LogService {
    @GET("/phonevar/api/user/{id}/log")
    fun getLogs(@Path("id") id: Int): Flowable<LogResponse>
}