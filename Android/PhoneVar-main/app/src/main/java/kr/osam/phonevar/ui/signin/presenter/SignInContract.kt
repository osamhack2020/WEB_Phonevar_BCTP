package kr.osam.phonevar.ui.signin.presenter

import androidx.annotation.StringRes
import kr.osam.phonevar.ui.BasePresenter
import kr.osam.phonevar.ui.BaseView

interface SignInContract {
    interface View : BaseView<Presenter> {
        fun showLoading()

        fun hideLoading()

        fun goToMainActivity()

        fun showMessage(@StringRes id: Int)

        fun showMessage(message: String)
    }

    interface Presenter : BasePresenter {
        fun register(uuid: String, name: String, serviceID: String)
    }
}