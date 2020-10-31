package kr.osam.admin.data.soldier

import io.reactivex.Flowable
import kr.osam.admin.data.SoldierItem
import kr.osam.admin.data.UnitItem

interface SoldierDataSource {
    fun getSoldierList() : Flowable<List<SoldierItem>>

    fun updateSoldierStatus(soldierItem: SoldierItem) : Flowable<SoldierItem>
}