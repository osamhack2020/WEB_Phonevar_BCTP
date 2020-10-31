package kr.osam.admin.ui.log

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.osam.admin.R
import kr.osam.admin.data.LogItem
import kr.osam.admin.data.LogState
import kr.osam.admin.ui.log.adapter.LogRecyclerViewAdapter
import kr.osam.admin.ui.log.presenter.LogContract
import kr.osam.admin.ui.log.presenter.LogPresenter
import kr.osam.admin.util.Injection
import kr.osam.core.activity.ActivityBase
import kr.osam.core.toast.ToastUtil

class LogBottomSheet : ActivityBase(), LogContract.View {
    override lateinit var presenter: LogContract.Presenter
    private lateinit var nameTextView: TextView
    private lateinit var logRecyclerView: RecyclerView
    private lateinit var bottomSheetCardView: LinearLayout
    private lateinit var behavior: BottomSheetBehavior<LinearLayout>
    private lateinit var logRecyclerViewAdapter: LogRecyclerViewAdapter
    private lateinit var bootCheckBox: CheckBox
    private lateinit var shutDownCheckBox: CheckBox
    private lateinit var serviceStartCheckBox: CheckBox
    private val logList: ArrayList<LogItem> = arrayListOf()
    private val logStateList: HashSet<LogState> = hashSetOf()
    private var name: String? = null
    private var id: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_bottom_sheet)
        name = intent.getStringExtra(NAME)
        id = intent.getIntExtra(USER_ID, -1)
        init()

    }

    private fun init() {
        logStateList.apply {
            add(LogState.SHUT_DOWN)
            add(LogState.START_SERVICE)
            add(LogState.BOOT)
        }
        LogPresenter(
            Injection.provideLogRepository(this),
            this,
            Injection.provideSchedulerProvider()
        )
        initView()
        initBottomSheet()
        initRecyclerView()
    }

    private fun initView() {
        bottomSheetCardView = findViewById(R.id.log_card_view)
        logRecyclerView = findViewById(R.id.log_recycler_view)
        nameTextView = findViewById(R.id.name_text_view)
        nameTextView.text = name ?: ""
        bootCheckBox = findViewById(R.id.boot_check_box)
        shutDownCheckBox = findViewById(R.id.shutdown_check_box)
        serviceStartCheckBox = findViewById(R.id.service_start_check_box)
        bootCheckBox.setOnClickListener { v: View? ->
            when ((v as CheckBox).isChecked) {
                false -> {
                    logStateList.remove(LogState.BOOT)
                }
                true -> {
                    logStateList.add(LogState.BOOT)
                }
            }
            getLogItem()
        }

        shutDownCheckBox.setOnClickListener { v: View? ->
            when ((v as CheckBox).isChecked) {
                false -> {
                    logStateList.remove(LogState.SHUT_DOWN)
                }
                true -> {
                    logStateList.add(LogState.SHUT_DOWN)
                }
            }
            getLogItem()
        }

        serviceStartCheckBox.setOnClickListener { v: View? ->
            when ((v as CheckBox).isChecked) {
                false -> {
                    logStateList.remove(LogState.START_SERVICE)
                }
                true -> {
                    logStateList.add(LogState.START_SERVICE)
                }
            }
            getLogItem()
        }
    }


    private fun initBottomSheet() {
        val maxHeight = (getWindowHeight() * BOTTOM_SHEET_SIZE_SHOW_PERCENT).toInt()
        behavior = BottomSheetBehavior.from(bottomSheetCardView)
        behavior.peekHeight = maxHeight
        val layoutParams = bottomSheetCardView.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight().toInt()
        bottomSheetCardView.layoutParams = layoutParams
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED, BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        finish()
                    }
                }
            }

            override fun onSlide(
                bottomSheet: View,
                slideOffset: Float
            ) {
            }
        })
    }

    private fun initRecyclerView() {
        logRecyclerViewAdapter = LogRecyclerViewAdapter(logList, this)
        logRecyclerView.adapter = logRecyclerViewAdapter
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
        getLogItem()
    }

    private fun getLogItem(){
        id?.apply {
            presenter.getLogList(this, logStateList.toList())
        }
    }

    override fun showLoading() {
        showProgressDialog(R.string.loading)
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun showMessage(id: Int) {
        ToastUtil.getInstance().makeShort(id)
    }

    override fun showMessage(message: String) {
        ToastUtil.getInstance().makeShort(message)
    }

    override fun showLogList(logList: List<LogItem>) {
        this.logList.clear()
        this.logList.addAll(logList)
        logRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun showLogLoadFail() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }


    private fun getBottomSheetDialogDefaultHeight(): Double {
        return getWindowHeight() * BOTTOM_SHEET_SIZE_EXPENDED_PERCENT
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

    companion object {
        const val NAME = "NAME"
        const val USER_ID = "USER_ID"
        const val BOTTOM_SHEET_SIZE_SHOW_PERCENT = 0.6
        const val BOTTOM_SHEET_SIZE_EXPENDED_PERCENT = 0.9
    }


}
