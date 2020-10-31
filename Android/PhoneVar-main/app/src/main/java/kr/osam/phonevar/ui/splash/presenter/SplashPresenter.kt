package kr.osam.phonevar.ui.splash.presenter

import io.reactivex.disposables.CompositeDisposable
import kr.osam.phonevar.data.UserItem
import kr.osam.phonevar.data.source.user.UserRepository
import kr.osam.phonevar.util.Schedulers.BaseSchedulerProvider

class SplashPresenter(
    val userRepository: UserRepository,
    val splashView: SplashContract.View,
    val schedulerProvider: BaseSchedulerProvider
) : SplashContract.Presenter {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        splashView.presenter = this
    }

    override fun subscribe() {
        loadUser()
    }

    private fun loadUser() {
        userRepository.refreshUser()
        compositeDisposable.add(
            userRepository
                .getUser()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    this::checkUserSaved
                ) { splashView.goToSignInActivity() })
    }

    private fun checkUserSaved(userList: List<UserItem>) {
        if (userList.isEmpty())
            splashView.goToSignInActivity()
        else
            splashView.goToMainActivity()
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}