package kr.osam.admin.ui.main.presenter

import androidx.annotation.StringRes
import kr.osam.admin.data.LogItem
import kr.osam.admin.data.SoldierItem
import kr.osam.admin.ui.main.MainActivity
import kr.osam.phonevar.ui.BasePresenter
import kr.osam.phonevar.ui.BaseView

interface MainContract {
    interface View : BaseView<Presenter> {
        fun showLoading()

        fun hideLoading()

        fun showSoldierList(soldierList : List<SoldierItem>,logState: MainActivity.SoldierState)

        fun showSoldierUpdateSuccess()

        fun showMessage(@StringRes id: Int)

        fun showMessage(message: String)

        fun goToSignIn()
    }

    interface Presenter : BasePresenter {
        fun getSoldierList(isChecked: Boolean)

        fun getSoldierList()

        fun updateSoldier(soldierItem: SoldierItem)

        fun logOut()
    }
}