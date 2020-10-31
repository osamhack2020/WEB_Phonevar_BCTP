package kr.osam.admin.ui.log.presenter

import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import kr.osam.admin.R
import kr.osam.admin.data.LogItem
import kr.osam.admin.data.LogState
import kr.osam.phonevar.data.source.log.LogRepository
import kr.osam.phonevar.util.Schedulers.BaseSchedulerProvider

class LogPresenter(
    val logRepository: LogRepository,
    val logView: LogContract.View,
    val schedulerProvider: BaseSchedulerProvider
) : LogContract.Presenter {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        logView.presenter = this
    }

    override fun getLogList(id: Int, logStateList: List<LogState>) {
        getLog(id, logStateList)
    }

    private fun getLog(id: Int, logStateList: List<LogState>) {
        logView.showLoading()
        compositeDisposable.add(logRepository.getLogList(id, logStateList)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ logItemList: List<LogItem> ->
                logView.showLogList(logItemList)
                logView.hideLoading()
            })
            {
                logView.hideLoading()
                logView.showMessage(R.string.log_failed)
                logView.showLogLoadFail()
            })
    }

    override fun subscribe() {
        logRepository.refreshLog()
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}