package kr.osam.admin.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import kr.osam.admin.R
import kr.osam.admin.data.SoldierItem
import kr.osam.admin.ui.log.LogBottomSheet
import kr.osam.admin.ui.main.adapter.RecyclerViewClick
import kr.osam.admin.ui.main.adapter.RecyclerViewOptionSelect
import kr.osam.admin.ui.main.adapter.SoldierRecyclerViewAdapter
import kr.osam.admin.ui.main.presenter.MainContract
import kr.osam.admin.ui.main.presenter.MainPresenter
import kr.osam.admin.ui.signin.SignInActivity
import kr.osam.admin.util.Injection
import kr.osam.core.activity.ActivityBase
import kr.osam.core.toast.ToastUtil
import kr.osam.phonevar.util.setupActionBar


class MainActivity : ActivityBase(), MainContract.View, RecyclerViewOptionSelect , RecyclerViewClick{
    override lateinit var presenter: MainContract.Presenter
    lateinit var soldierRecyclerView: RecyclerView
    lateinit var soldierRecyclerViewAdapter: SoldierRecyclerViewAdapter
    lateinit var menu: Menu
    var soldierItemList = arrayListOf<SoldierItem>()
    var currentSate: SoldierState = SoldierState.SHOW_ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.unit_crew_list)
        }

        MainPresenter(
            Injection.provideSoldierRepository(this),
            this,
            Injection.provideSchedulerProvider()
        )
        init()
    }

    private fun init() {
        soldierRecyclerView = findViewById(R.id.log_recycler_view)
        soldierRecyclerViewAdapter = SoldierRecyclerViewAdapter(
            this,
            soldierItemList = soldierItemList,
            recyclerViewOptionSelect = this,
            recyclerViewClick = this
        )
        soldierRecyclerView.adapter = soldierRecyclerViewAdapter

    }

    override fun onRecyclerViewClickedListener(position: Int) {
        Intent(this@MainActivity, LogBottomSheet::class.java).apply {
            putExtra(LogBottomSheet.NAME , soldierItemList[position].name)
            putExtra(LogBottomSheet.USER_ID , soldierItemList[position].id.toInt())
            startActivity(this)
        }
    }

    override fun onRecyclerViewOptionSelectListener(position: Int, menuID: Int) {
        when (menuID) {
            R.id.check_menu -> {
                presenter.updateSoldier(soldierItemList[position].apply {
                    statusCode = "200"
                })
            }
            R.id.uncheck_menu -> {
                presenter.updateSoldier(soldierItemList[position].apply {
                    statusCode = "400"
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

    override fun goToSignIn() {
        Intent(this@MainActivity, SignInActivity::class.java).apply {
            finish()
            startActivity(this)
        }
    }

    override fun showSoldierUpdateSuccess() {
        ToastUtil.getInstance().makeShort(R.string.state_change_success)
        when (currentSate) {
            SoldierState.SHOW_ALL -> {
                presenter.getSoldierList()
            }
            SoldierState.SHOW_UNCHECKED -> {
                presenter.getSoldierList(false)
            }
            SoldierState.SHOW_CHECKED -> {
                presenter.getSoldierList(true)
            }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu!!
        menuInflater.inflate(R.menu.main_menu, menu)
        updateOptionMenu(currentSate)
        return super.onCreateOptionsMenu(menu)
    }

    override fun showSoldierList(soldierList: List<SoldierItem>, logState: SoldierState) {
        this.soldierItemList.clear()
        this.soldierItemList.addAll(soldierList)
        soldierRecyclerViewAdapter.notifyDataSetChanged()
        updateOptionMenu(logState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.refresh_menu -> {
                when (currentSate) {
                    SoldierState.SHOW_ALL -> presenter.getSoldierList()
                    SoldierState.SHOW_CHECKED -> presenter.getSoldierList(true)
                    else -> presenter.getSoldierList(false)
                }
            }
            R.id.show_all_menu -> {
                presenter.getSoldierList()
            }
            R.id.show_unchecked_menu -> {
                presenter.getSoldierList(false)
            }
            R.id.show_checked_menu -> {
                presenter.getSoldierList(true)
            }
            R.id.log_out_menu -> {
                presenter.logOut()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateOptionMenu(logState: SoldierState) {
        currentSate = logState
        when (logState) {
            SoldierState.SHOW_ALL -> {
                hideOption(R.id.show_all_menu)
                showOption(R.id.show_unchecked_menu)
                showOption(R.id.show_checked_menu)
            }

            SoldierState.SHOW_UNCHECKED -> {
                showOption(R.id.show_all_menu)
                hideOption(R.id.show_unchecked_menu)
                showOption(R.id.show_checked_menu)
            }

            SoldierState.SHOW_CHECKED -> {
                showOption(R.id.show_all_menu)
                showOption(R.id.show_unchecked_menu)
                hideOption(R.id.show_checked_menu)
            }
        }
    }

    private fun hideOption(id: Int) {
        if (!this::menu.isInitialized) return
        val item: MenuItem = menu.findItem(id)
        item.isVisible = false
    }

    private fun showOption(id: Int) {
        if (!this::menu.isInitialized) return
        val item: MenuItem = menu.findItem(id)
        item.isVisible = true
    }


    companion object {
        const val TAG = "MainActivity"
    }

    enum class SoldierState {
        SHOW_ALL,
        SHOW_CHECKED,
        SHOW_UNCHECKED
    }

}
