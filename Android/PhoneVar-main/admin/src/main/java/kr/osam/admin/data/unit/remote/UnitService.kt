package kr.osam.admin.data.unit.remote

import io.reactivex.Flowable
import retrofit2.http.GET

interface UnitService {
    @GET("/phonevar/api/unit/list")
    fun getUnitList(): Flowable<List<UnitResponse>>

}