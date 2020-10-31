package kr.osam.admin.data.soldier.remote

import com.google.gson.JsonObject
import io.reactivex.Flowable
import kr.osam.admin.data.SoldierItem
import kr.osam.admin.data.shared.RegisterUnitSharedPreference
import kr.osam.admin.data.soldier.SoldierDataSource
import kr.osam.admin.util.RetrofitManager

object SoldierRemoteDataSource : SoldierDataSource {
    override fun getSoldierList(): Flowable<List<SoldierItem>> {
        val unitID = RegisterUnitSharedPreference.getInstance().unitID
        return RetrofitManager.getInstance()
            .retrofit
            .create(SoldierService::class.java)
            .getSoldierList(unitID.toInt())
            .flatMap { list ->
                Flowable.fromIterable(list)
                    .map { item -> item.convertToModel() }
                    .toList()
                    .toFlowable()
            }
    }


    override fun updateSoldierStatus(soldierItem: SoldierItem): Flowable<SoldierItem> {
        var body: JsonObject = JsonObject().apply {
            addProperty("id", soldierItem.id.toInt())
            addProperty("statusCode", soldierItem.statusCode.toInt())
        }

        return RetrofitManager.getInstance()
            .retrofit
            .create(SoldierService::class.java)
            .updateSoldier(body)
            .map { soldierItem -> soldierItem.convertToModel() }
    }
}