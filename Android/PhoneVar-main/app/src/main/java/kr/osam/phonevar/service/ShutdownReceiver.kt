package com.yunjaena.observe

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kr.osam.phonevar.Injection
import kr.osam.phonevar.data.LogItem
import kr.osam.phonevar.data.source.log.local.LogDatabase

class ShutdownReceiver : BroadcastReceiver() {
    private val TAG = "ShutdownReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_SHUTDOWN.equals(intent?.action)) {
            Log.d(TAG, "shutting down")
            context?.apply {
                Injection.provideLogRepository(context).saveLog(LogItem().apply {
                    logType = 2
                })
            }
        }
    }
}
