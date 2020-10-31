package kr.osam.phonevar.ui.main.presenter

import io.reactivex.disposables.CompositeDisposable
import kr.osam.phonevar.data.LogItem
import kr.osam.phonevar.data.source.log.LogRepository
import kr.osam.phonevar.util.Schedulers.BaseSchedulerProvider

class MainPresenter(
    val logRepository: LogRepository,
    val mainView: MainContract.View,
    val schedulerProvider: BaseSchedulerProvider
) : MainContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        mainView.presenter = this
    }

    private fun getLog() {
        mainView.showLoading()
        logRepository.cacheIsDirty = true
        compositeDisposable.add(logRepository.getLogList()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(this::showLogItem) {
                mainView.hideLoading()
            })
    }

    private fun showLogItem(item: List<LogItem>) {
        mainView.showLogItem(item)
        mainView.hideLoading()
    }

    override fun updateLog() {
        getLog()
    }

    override fun subscribe() {
        getLog()
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}