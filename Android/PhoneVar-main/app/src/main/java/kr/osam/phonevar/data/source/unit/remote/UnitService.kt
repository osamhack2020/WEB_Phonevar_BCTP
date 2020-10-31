package kr.osam.phonevar.data.source.unit.local

import io.reactivex.Flowable
import retrofit2.http.GET

interface UnitService {
    @GET("/phonevar/api/unit/list")
    fun getUnitList(): Flowable<List<UnitResponse>>

}