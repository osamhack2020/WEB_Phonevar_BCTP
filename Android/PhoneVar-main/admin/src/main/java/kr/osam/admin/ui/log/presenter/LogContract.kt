package kr.osam.admin.ui.log.presenter

import androidx.annotation.StringRes
import kr.osam.admin.data.LogItem
import kr.osam.admin.data.LogState
import kr.osam.phonevar.ui.BasePresenter
import kr.osam.phonevar.ui.BaseView

interface LogContract {
    interface View : BaseView<Presenter> {
        fun showLoading()

        fun hideLoading()

        fun showLogList(logList: List<LogItem>)

        fun showLogLoadFail()

        fun showMessage(@StringRes id: Int)

        fun showMessage(message: String)

    }

    interface Presenter : BasePresenter {
        fun getLogList(id: Int, logStateList: List<LogState>)
    }
}