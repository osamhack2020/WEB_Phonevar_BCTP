package kr.osam.admin.ui.signin.presenter

import androidx.annotation.StringRes
import kr.osam.admin.data.UnitItem
import kr.osam.phonevar.ui.BasePresenter
import kr.osam.phonevar.ui.BaseView

interface SignInContract {
    interface View : BaseView<Presenter> {
        fun showLoading()

        fun hideLoading()

        fun showUnitItem(unitList: List<UnitItem>)

        fun showMessage(@StringRes id: Int)

        fun showMessage(message: String)

        fun goToMain()
    }

    interface Presenter : BasePresenter {
        fun getUnit()

        fun registerUnit(name: String, code: String)
    }
}