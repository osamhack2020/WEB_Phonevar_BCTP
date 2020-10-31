package kr.osam.phonevar.data.source.unit

import androidx.annotation.VisibleForTesting
import io.reactivex.Flowable
import kr.osam.phonevar.data.UnitItem


class UnitRepository(
    private val unitRemoteDataSource: UnitDataSource,
    private val unitLocalDataSource: UnitDataSource
) : UnitDataSource {

    @VisibleForTesting
    var cachedUnit: LinkedHashMap<Int, UnitItem> = LinkedHashMap()
    @VisibleForTesting
    var cacheIsDirty = false

    override fun getUnits(): Flowable<List<UnitItem>> {
        if (!cacheIsDirty)
            return Flowable.fromIterable(cachedUnit.values).toList().toFlowable()

        val remoteUnits: Flowable<List<UnitItem>> = getAndSaveRemoteUnits()

        return if (cacheIsDirty) {
            remoteUnits
        } else {
            val localUnits: Flowable<List<UnitItem>> = getAndCacheLocalUnits()
            Flowable.concat(localUnits, remoteUnits)
                .filter { units: List<UnitItem?> -> units.isNotEmpty() }
                .firstOrError()
                .toFlowable()
        }
    }

    override fun saveUnit(unitItem: UnitItem) {
        unitRemoteDataSource.saveUnit(unitItem)
        unitLocalDataSource.saveUnit(unitItem)
        cachedUnit[unitItem.id.toInt()] = unitItem
    }


    private fun getAndCacheLocalUnits(): Flowable<List<UnitItem>> =
        unitLocalDataSource.getUnits()
            .flatMap {
                Flowable.fromIterable(it)
            }.doOnNext { t: UnitItem ->
                cachedUnit[t.id.toInt()] = t
            }.toList()
            .toFlowable()

    private fun getAndSaveRemoteUnits(): Flowable<List<UnitItem>> =
        unitRemoteDataSource
            .getUnits()
            .flatMap { units ->
                Flowable.fromIterable(units).doOnNext { unit ->
                    run {
                        unitLocalDataSource.saveUnit(unit)
                        cachedUnit.put(unit.id.toInt(), unit)
                    }
                }
            }.toList().toFlowable().doOnComplete {
                cacheIsDirty = false
            }

    override fun refreshUnits() {
        cacheIsDirty = true
    }

    companion object {
        private var INSTANCE: UnitRepository? = null

        @JvmStatic
        fun getInstance(
            unitRemoteDataSource: UnitDataSource,
            unitLocalDataSource: UnitDataSource
        ): UnitRepository {
            return INSTANCE ?: UnitRepository(unitRemoteDataSource, unitLocalDataSource)
                .apply { INSTANCE = this }
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}