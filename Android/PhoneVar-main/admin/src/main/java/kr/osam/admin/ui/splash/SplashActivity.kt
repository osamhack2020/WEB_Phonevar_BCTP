package kr.osam.admin.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import kr.osam.admin.R
import kr.osam.admin.ui.main.MainActivity
import kr.osam.admin.ui.signin.SignInActivity
import kr.osam.admin.ui.splash.presenter.SplashContract
import kr.osam.admin.ui.splash.presenter.SplashPresenter
import kr.osam.core.activity.ActivityBase
import kr.osam.core.toast.ToastUtil

class SplashActivity : ActivityBase(), SplashContract.View {
    override lateinit var presenter: SplashContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SplashPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun showLoading() {
        showProgressDialog(R.string.loading)
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun goToMainActivity() {
        Handler().postDelayed({
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }, 2000)
    }

    override fun goToSignInActivity() {
        Handler().postDelayed({
            Intent(this, SignInActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }, 2000)
    }

    override fun showMessage(id: Int) {
        ToastUtil.getInstance().makeShort(id)
    }

    override fun showMessage(message: String) {
        ToastUtil.getInstance().makeShort(message)
    }


}
