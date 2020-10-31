package kr.osam.phonevar.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kr.osam.phonevar.Injection
import kr.osam.phonevar.data.LogItem

class CheckPhoneIsOnAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.apply {
            Injection.provideLogRepository(applicationContext).saveLog(LogItem().apply {
                logType = 1
            })
        }
    }
}