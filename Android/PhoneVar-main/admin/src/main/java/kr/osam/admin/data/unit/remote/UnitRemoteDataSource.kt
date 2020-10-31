package kr.osam.admin.data.unit.remote

import io.reactivex.Flowable
import kr.osam.admin.data.UnitItem
import kr.osam.admin.data.unit.UnitDataSource
import kr.osam.admin.util.RetrofitManager


object UnitRemoteDataSource : UnitDataSource {
    override fun getUnits(): Flowable<List<UnitItem>> = RetrofitManager.getInstance()
        .retrofit
        .create(UnitService::class.java)
        .getUnitList()
        .flatMap { list ->
            Flowable.fromIterable(list)
                .map { item -> item.convertToModel() }
                .toList()
                .toFlowable()
        }

    override fun saveUnit(unitItem: UnitItem) {
        // Not required
    }

    override fun refreshUnits() {
        // Not required
    }
}