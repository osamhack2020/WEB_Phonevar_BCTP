package kr.osam.admin.ui.splash.presenter

import androidx.annotation.StringRes
import kr.osam.phonevar.ui.BasePresenter
import kr.osam.phonevar.ui.BaseView

interface SplashContract {
    interface View : BaseView<Presenter> {
        fun showLoading()

        fun hideLoading()

        fun goToMainActivity()

        fun goToSignInActivity()

        fun showMessage(@StringRes id: Int)

        fun showMessage(message: String)
    }

    interface Presenter : BasePresenter
}