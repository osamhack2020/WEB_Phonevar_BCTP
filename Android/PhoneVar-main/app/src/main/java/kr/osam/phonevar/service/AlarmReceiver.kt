package com.yunjaena.observe

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

private const val TAG = "AlarmReceiver"

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent(context, ManagementService::class.java).let {
                context?.startForegroundService(it)
                Log.d(TAG, "startService" + Build.VERSION.SDK_INT.toString())
            }
        } else {
            Intent(context, ManagementService::class.java).let {
                context?.startService(it)
                Log.d(TAG, "startService" + Build.VERSION.SDK_INT.toString())
            }
        }
    }
}