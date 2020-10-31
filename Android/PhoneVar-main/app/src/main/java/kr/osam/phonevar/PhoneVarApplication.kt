package kr.osam.phonevar

import android.app.Application
import kr.osam.core.toast.ToastUtil
import kr.osam.phonevar.util.TokenSharedPreference

class PhoneVarApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        ToastUtil.getInstance().init(this)
        TokenSharedPreference.getInstance().init(this)
    }
}