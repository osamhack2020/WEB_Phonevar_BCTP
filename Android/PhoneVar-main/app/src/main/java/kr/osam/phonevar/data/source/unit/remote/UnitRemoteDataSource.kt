package kr.osam.phonevar.data.source.unit.remote

import io.reactivex.Flowable
import kr.osam.phonevar.data.UnitItem
import kr.osam.phonevar.data.source.unit.UnitDataSource
import kr.osam.phonevar.data.source.unit.local.UnitService
import kr.osam.phonevar.data.source.unit.local.convertToModel
import kr.osam.phonevar.util.RetrofitManager

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