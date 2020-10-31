package kr.osam.phonevar.ui.splash.presenter

import androidx.annotation.StringRes
import kr.osam.phonevar.ui.BasePresenter
import kr.osam.phonevar.ui.BaseView

interface SplashContract {
    interface View : BaseView<Presenter> {
        fun goToMainActivity()

        fun goToSignInActivity()

        fun showMessage(@StringRes id: Int)

        fun showMessage(message: String)
    }

    interface Presenter : BasePresenter
}