package kr.osam.phonevar.ui.main.presenter

import androidx.annotation.StringRes
import kr.osam.phonevar.data.LogItem
import kr.osam.phonevar.ui.BasePresenter
import kr.osam.phonevar.ui.BaseView

interface MainContract {
    interface View : BaseView<Presenter> {
        fun showLoading()

        fun hideLoading()

        fun showLogItem(logList: List<LogItem>)

        fun showMessage(@StringRes id: Int)

        fun showMessage(message: String)
    }

    interface Presenter : BasePresenter {
        fun updateLog()
    }
}