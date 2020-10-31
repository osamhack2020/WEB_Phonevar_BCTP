package kr.osam.admin.ui.signin.presenter

import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.disposables.CompositeDisposable
import kr.osam.admin.R
import kr.osam.admin.data.UnitItem
import kr.osam.admin.data.shared.RegisterUnitSharedPreference
import kr.osam.phonevar.data.source.unit.UnitRepository
import kr.osam.phonevar.util.Schedulers.BaseSchedulerProvider

class SignInPresenter(
    val unitRepository: UnitRepository,
    val signInView: SignInContract.View,
    val schedulerProvider: BaseSchedulerProvider
) : SignInContract.Presenter {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        signInView.presenter = this
    }

    private fun getUnitList() {
        signInView.showLoading()
        compositeDisposable.add(unitRepository.getUnits()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(this::updateUnitList) {
                signInView.showMessage(R.string.error_loading_unit)
                signInView.hideLoading()
            })
    }

    private fun updateUnitList(unitList: List<UnitItem>) {
        signInView.showUnitItem(unitList)
        signInView.hideLoading()
    }

    override fun getUnit() {
        getUnitList()
    }

    override fun registerUnit(name: String, code: String) {
        signInView.showLoading()
        FirebaseMessaging.getInstance().subscribeToTopic(code)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    signInView.showMessage(R.string.register_failed)
                    signInView.hideLoading()
                    return@addOnCompleteListener
                }
                RegisterUnitSharedPreference.getInstance().saveUnitID(code)
                RegisterUnitSharedPreference.getInstance().saveUnitName(name)
                signInView.hideLoading()
                signInView.goToMain()
            }
    }

    override fun subscribe() {
        unitRepository.cacheIsDirty = true
        getUnitList()
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}