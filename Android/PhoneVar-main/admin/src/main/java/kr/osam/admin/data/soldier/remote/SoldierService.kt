package kr.osam.admin.data.soldier.remote

import com.google.gson.JsonObject
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface SoldierService {
    @GET("/phonevar/api/user/list")
    fun getSoldierList(@Query("unitCode") unitCode: Int): Flowable<List<SoldierResponse>>

    @PUT("/phonevar/api/user")
    fun updateSoldier(@Body jsonBody: JsonObject): Flowable<SoldierResponse>
}