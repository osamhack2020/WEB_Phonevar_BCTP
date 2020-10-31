package com.yunjaena.observe

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import kr.osam.phonevar.Injection
import kr.osam.phonevar.data.LogItem
import kr.osam.phonevar.data.source.log.local.LogDatabase

private const val TAG = "BootedBroadCastReceiver"

class BootedBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d(TAG, "booted")
            startService(context!!)
            Injection.provideLogRepository(context).saveLog(LogItem().apply{
                logType = 0
            })
        }
    }

    private fun startService(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent(context, ManagementService::class.java).let {
                context.startForegroundService(it)
                Log.d(TAG, "startService" + Build.VERSION.SDK_INT.toString())
            }
        } else {
            Intent(context, ManagementService::class.java).let {
                context.startService(it)
                Log.d(TAG, "startService" + Build.VERSION.SDK_INT.toString())
            }
        }
    }
}