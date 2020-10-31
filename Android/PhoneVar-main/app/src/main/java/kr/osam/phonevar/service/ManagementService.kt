package com.yunjaena.observe

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kr.osam.phonevar.Injection
import kr.osam.phonevar.R
import kr.osam.phonevar.data.LogItem
import kr.osam.phonevar.service.CheckPhoneIsOnAlarmReceiver
import kr.osam.phonevar.service.TurnOffAlarmReceiver
import kr.osam.phonevar.ui.splash.SplashActivity
import java.util.*


private const val CHANNEL_ID = "100"

class ManagementService : Service() {
    var shutdownReceiver: BroadcastReceiver? = null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        registerShutdownBroadCast()
        registerNetworkConnectEvent()
        startLogging()
        registerTurnOffAlarm()
        registerCheckPhoneIsOn()
        return START_STICKY
    }

    private fun registerShutdownBroadCast() {
        val filter = IntentFilter(Intent.ACTION_SHUTDOWN)
        shutdownReceiver = ShutdownReceiver()
        registerReceiver(shutdownReceiver, filter)
    }

    private fun registerNetworkConnectEvent() {
        val cm = applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiNetworkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Injection.provideLogRepository(applicationContext).syncLog()
            }

            override fun onLost(network: Network?) {
            }
        }
        cm.registerNetworkCallback(wifiNetworkRequest, callback)
    }

    private fun startLogging() {
        Injection.provideLogRepository(applicationContext).saveLog(LogItem().apply {
            logType = 1
        })
    }

    private fun registerTurnOffAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, TurnOffAlarmReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 20)
            set(Calendar.MINUTE, 50)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pIntent
        )
    }

    private fun registerCheckPhoneIsOn(){
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, CheckPhoneIsOnAlarmReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(this, 2, intent, 0)
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 21)
            set(Calendar.MINUTE, 10)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pIntent
        )
    }


    private fun unregisterTurnOffAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, TurnOffAlarmReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.cancel(pIntent)
    }

    private fun startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
            val intent = Intent(this, SplashActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.channel_description))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
            startForeground(100, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                setSound(null, null)
                setShowBadge(false)
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        restartService()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        restartService()
    }

    private fun restartService() {
        unregisterReceiver(shutdownReceiver)
        Log.d("ManagementService", "restart")
        val c: Calendar = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis()
        c.add(Calendar.SECOND, 1)
        val intent = Intent(this, AlarmReceiver::class.java)
        val sender = PendingIntent.getBroadcast(this, 0, intent, 0)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, c.timeInMillis, sender)
    }

    companion object {
        const val TAG = "ManagemnetService"
    }
}