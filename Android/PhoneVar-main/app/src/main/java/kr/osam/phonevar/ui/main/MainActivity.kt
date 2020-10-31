package kr.osam.phonevar.ui.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.yunjaena.observe.ManagementService
import kr.osam.core.activity.ActivityBase
import kr.osam.core.toast.ToastUtil
import kr.osam.phonevar.Injection
import kr.osam.phonevar.R
import kr.osam.phonevar.data.LogItem
import kr.osam.phonevar.ui.main.adapter.LogRecyclerViewAdapter
import kr.osam.phonevar.ui.main.presenter.MainContract
import kr.osam.phonevar.ui.main.presenter.MainPresenter
import kr.osam.phonevar.util.setupActionBar

class MainActivity : ActivityBase(), MainContract.View {

    override lateinit var presenter: MainContract.Presenter
    lateinit var adapter: LogRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    private val logList: ArrayList<LogItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        startService(this)
    }

    private fun init() {
        recyclerView = findViewById(R.id.log_recycler_view)
        adapter = LogRecyclerViewAdapter(logList)
        recyclerView.adapter = adapter
        MainPresenter(
            Injection.provideLogRepository(this),
            this,
            Injection.provideSchedulerProvider()
        )
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.active_log)
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

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.refresh_menu -> {
                presenter.updateLog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading() {
        showProgressDialog(R.string.loading)
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun showLogItem(logList: List<LogItem>) {
        this.logList.clear()
        this.logList.addAll(logList)
        this.adapter.notifyDataSetChanged()

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

    companion object {
        const val TAG = "MainActivity"
    }
}
