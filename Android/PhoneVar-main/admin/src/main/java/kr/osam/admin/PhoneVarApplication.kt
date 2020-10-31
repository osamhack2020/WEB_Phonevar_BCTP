package kr.osam.admin

import android.app.Application
import kr.osam.admin.data.shared.RegisterUnitSharedPreference
import kr.osam.core.toast.ToastUtil

class PhoneVarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RegisterUnitSharedPreference.getInstance().init(this)
        ToastUtil.getInstance().init(this)
    }
}