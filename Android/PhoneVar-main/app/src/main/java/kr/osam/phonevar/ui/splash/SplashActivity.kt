package kr.osam.phonevar.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import kr.osam.core.activity.ActivityBase
import kr.osam.core.toast.ToastUtil
import kr.osam.phonevar.Injection
import kr.osam.phonevar.ui.main.MainActivity
import kr.osam.phonevar.R
import kr.osam.phonevar.ui.signin.SignInActivity
import kr.osam.phonevar.ui.splash.presenter.SplashContract
import kr.osam.phonevar.ui.splash.presenter.SplashPresenter

class SplashActivity : ActivityBase(), SplashContract.View {

    override lateinit var presenter: SplashContract.Presenter

    // TODO 데이터 가져오기
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SplashPresenter(
            Injection.provideUserRepository(applicationContext),
            this,
            Injection.provideSchedulerProvider()
        )
    }


    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun goToSignInActivity() {
        Handler().postDelayed({
            Intent(this@SplashActivity, SignInActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }, 2000)
    }

    override fun goToMainActivity() {
        Handler().postDelayed({
            Intent(this@SplashActivity, MainActivity::class.java).apply {
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

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }


}
