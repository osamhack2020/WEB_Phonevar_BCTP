package kr.osam.admin.ui.splash.presenter

import kr.osam.admin.data.shared.RegisterUnitSharedPreference

class SplashPresenter(
    val splashView: SplashContract.View
) : SplashContract.Presenter {

    init {
        splashView.presenter = this
    }

    private fun checkUnitCodeSaved() {
        var unit = RegisterUnitSharedPreference.getInstance().unitName
        if (unit.isNullOrEmpty()) {
            splashView.goToSignInActivity()
        } else {
            splashView.goToMainActivity()
        }
    }

    override fun subscribe() {
        checkUnitCodeSaved()
    }

    override fun unsubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}