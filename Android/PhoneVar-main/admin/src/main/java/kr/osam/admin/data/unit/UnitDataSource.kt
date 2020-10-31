package kr.osam.admin.data.unit

import io.reactivex.Flowable
import kr.osam.admin.data.UnitItem

interface UnitDataSource {
    fun getUnits() : Flowable<List<UnitItem>>

    fun saveUnit(unitItem: UnitItem)

    fun refreshUnits()
}