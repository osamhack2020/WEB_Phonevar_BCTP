package kr.osam.phonevar.data.source.unit

import io.reactivex.Flowable
import kr.osam.phonevar.data.UnitItem

interface UnitDataSource {
    fun getUnits() : Flowable<List<UnitItem>>

    fun saveUnit(unitItem: UnitItem)

    fun refreshUnits()
}