package kr.osam.admin.data.soldier

import androidx.annotation.VisibleForTesting
import io.reactivex.Flowable
import kr.osam.admin.data.SoldierItem

class SoldierRepository(
    private val soldierRemoteDataSource: SoldierDataSource
) : SoldierDataSource {
    @VisibleForTesting
    var cachedUnit: LinkedHashMap<Int, SoldierItem> = LinkedHashMap()
    @VisibleForTesting
    var cacheIsDirty = false

    override fun getSoldierList(): Flowable<List<SoldierItem>> {
        if (!cacheIsDirty)
            return Flowable.fromIterable(cachedUnit.values).toList().toFlowable()

        return getAndSaveRemoteSoldier()
    }

    override fun updateSoldierStatus(soldierItem: SoldierItem) =
        soldierRemoteDataSource.updateSoldierStatus(soldierItem)

    private fun getAndSaveRemoteSoldier(): Flowable<List<SoldierItem>> =
        soldierRemoteDataSource
            .getSoldierList()
            .flatMap { soldiers ->
                Flowable.fromIterable(soldiers).doOnNext { soldier ->
                    run {
                        cachedUnit.put(soldier.id.toInt(), soldier)
                    }
                }
            }.toList().toFlowable().doOnComplete {
                cacheIsDirty = false
            }

    companion object {
        private var INSTANCE: SoldierRepository? = null

        @JvmStatic
        fun getInstance(
            soldierRemoteDataSource: SoldierDataSource
        ): SoldierRepository {
            return INSTANCE ?: SoldierRepository(soldierRemoteDataSource)
                .apply { INSTANCE = this }
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}