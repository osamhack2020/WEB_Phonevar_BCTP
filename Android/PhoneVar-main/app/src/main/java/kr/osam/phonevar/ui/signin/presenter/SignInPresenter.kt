package kr.osam.phonevar.ui.signin.presenter

import io.reactivex.disposables.CompositeDisposable
import kr.osam.phonevar.R
import kr.osam.phonevar.data.UserItem
import kr.osam.phonevar.data.source.user.UserRepository
import kr.osam.phonevar.util.Schedulers.BaseSchedulerProvider

class SignInPresenter(
    val userRepository: UserRepository,
    val signInView: SignInContract.View,
    val schedulerProvider: BaseSchedulerProvider
) : SignInContract.Presenter {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        signInView.presenter = this
    }


    override fun subscribe() {}


    override fun register(uuid: String, name: String, serviceID: String) {
        signInView.showLoading()
        compositeDisposable.add(userRepository.getUser(
            UserItem(
                deviceUUID = uuid,
                name = name,
                serviceNumber = serviceID
            )
        ).subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(this::checkUserSaved)
            {
                signInView.showMessage(R.string.register_failed)
                signInView.hideLoading()
            })
    }

    private fun checkUserSaved(userItemList: List<UserItem>) {
        if (userItemList.isNullOrEmpty())
            signInView.showMessage(R.string.register_failed)
        else
            signInView.goToMainActivity()

        signInView.hideLoading()
    }


    override fun unsubscribe() {

    }
}