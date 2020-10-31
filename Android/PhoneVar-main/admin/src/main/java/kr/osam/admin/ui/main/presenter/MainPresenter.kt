package kr.osam.admin.ui.main.presenter

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import kr.osam.admin.R
import kr.osam.admin.data.SoldierItem
import kr.osam.admin.data.shared.RegisterUnitSharedPreference
import kr.osam.admin.data.soldier.SoldierRepository
import kr.osam.admin.ui.main.MainActivity
import kr.osam.phonevar.util.Schedulers.BaseSchedulerProvider

class MainPresenter(
    val soldierRepository: SoldierRepository,
    val mainView: MainContract.View,
    val schedulerProvider: BaseSchedulerProvider
) : MainContract.Presenter {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        mainView.presenter = this
    }

    private fun getSoldierListItem() {
        soldierRepository.cacheIsDirty = true
        compositeDisposable.add(soldierRepository.getSoldierList()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ soldierList ->
                showSoldierList(
                    soldierList,
                    MainActivity.SoldierState.SHOW_ALL
                )
            }) {
                Log.e(TAG, it?.message)
                mainView.hideLoading()
                mainView.showMessage(R.string.soldier_load_failed)
            })
    }

    private fun getSoldierListItem(isChecked: Boolean) {
        soldierRepository.cacheIsDirty = true
        compositeDisposable.add(soldierRepository.getSoldierList()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .flatMap { soldierList ->
                Flowable.fromIterable(soldierList)
                    .filter { soldier -> soldier.statusCode == if (isChecked) "200" else "400" }
            }.toList()
            .subscribe({ logList ->
                showSoldierList(
                    logList,
                    if (isChecked) MainActivity.SoldierState.SHOW_CHECKED else MainActivity.SoldierState.SHOW_UNCHECKED
                )
            }) {
                Log.e(TAG, it?.message)
                mainView.hideLoading()
                mainView.showMessage(R.string.soldier_load_failed)
            })
    }

    override fun updateSoldier(soldierItem: SoldierItem) {
        mainView.showLoading()
        compositeDisposable.add(soldierRepository.updateSoldierStatus(soldierItem)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ soldier ->
                mainView.hideLoading()
                mainView.showSoldierUpdateSuccess()
            })
            {
                mainView.showMessage(R.string.state_change_failed)
                mainView.hideLoading()
            }

        )
    }

    private fun showSoldierList(
        soldierItemList: List<SoldierItem>,
        soldierState: MainActivity.SoldierState
    ) {
        mainView.showSoldierList(soldierItemList, soldierState)
        mainView.hideLoading()
    }

    override fun getSoldierList(isChecked: Boolean) {
        getSoldierListItem(isChecked)
    }

    override fun getSoldierList() {
        getSoldierListItem()
    }

    override fun logOut() {
        mainView.showLoading()
        val channel = RegisterUnitSharedPreference.getInstance().unitID
        Log.d("main", channel)
        FirebaseMessaging.getInstance().unsubscribeFromTopic(channel)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    mainView.showMessage(R.string.logout_failed)
                    mainView.hideLoading()
                    return@addOnCompleteListener
                }
                RegisterUnitSharedPreference.getInstance().removeAll()
                mainView.hideLoading()
                mainView.goToSignIn()
            }
    }


    override fun subscribe() {
        getSoldierListItem()
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    companion object {
        const val TAG = "MainPresenter"
    }
}