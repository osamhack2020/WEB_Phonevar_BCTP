package kr.osam.admin.ui.signin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import kr.osam.admin.R
import kr.osam.admin.data.UnitItem
import kr.osam.admin.ui.koreanindexer.KoreanIndexerRecyclerView
import kr.osam.admin.ui.main.MainActivity
import kr.osam.admin.ui.signin.adapter.UnitBottomSheetAdapter
import kr.osam.admin.ui.signin.presenter.SignInContract
import kr.osam.admin.ui.signin.presenter.SignInPresenter
import kr.osam.admin.util.Injection
import kr.osam.core.activity.ActivityBase
import kr.osam.core.toast.ToastUtil

class SignInActivity : ActivityBase(), SignInContract.View {
    override lateinit var presenter: SignInContract.Presenter
    private lateinit var registerButton: Button
    private lateinit var unitBottomSheetAdapter: UnitBottomSheetAdapter
    private lateinit var bottomSheetCardView: LinearLayout
    private lateinit var titleTextView: TextView
    private lateinit var inputEditText: EditText
    private lateinit var koreanIndexerRecyclerView: KoreanIndexerRecyclerView
    private lateinit var recyclerViewTitleTextView: TextView
    private lateinit var unitBottomSheetBehavior: UnitBottomSheetAdapter
    private lateinit var behavior: BottomSheetBehavior<LinearLayout>
    private var isSectionUse: Boolean = true
    private var unitList: ArrayList<UnitItem> = arrayListOf()
    private var searchUnitList: ArrayList<UnitItem> = arrayListOf()
    private lateinit var unitSelectTextView: TextView
    private var selectUnit: UnitItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        init()
    }

    private fun init() {
        SignInPresenter(
            Injection.provideUnitRepository(this),
            this,
            Injection.provideSchedulerProvider()

        )
        initView()
        initBottomSheet()
        initRecyclerView()
    }

    private fun initView() {
        unitSelectTextView = findViewById(R.id.unit_select_text_view)
        registerButton = findViewById(R.id.register_button)
        bottomSheetCardView = findViewById(R.id.unit_card_view)
        titleTextView = findViewById(R.id.title)
        inputEditText = findViewById(R.id.search_edit_text)
        koreanIndexerRecyclerView = findViewById(R.id.bottom_sheet_recycler_view)
        recyclerViewTitleTextView = findViewById(R.id.recycler_view_title_text_view)
        titleTextView.text = "부대선택"
        inputEditText.hint = "부대명을 입력해주세요."
        recyclerViewTitleTextView.hint = "부대명"
        unitSelectTextView.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) = Unit

            override fun afterTextChanged(s: Editable) {
                updateRecyclerViewItem()
            }
        })
        registerButton.setOnClickListener {
            if (selectUnit == null) {
                ToastUtil.getInstance().makeShort("부대를 선택해주세요.")
                return@setOnClickListener
            }
            selectUnit?.apply {
                presenter.registerUnit(unitName, unitCode)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun goToMain() {
       Intent(this, MainActivity::class.java).apply {
           startActivity(this)
           finish()
       }
    }

    override fun showLoading() {
        showProgressDialog(R.string.loading)
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun showUnitItem(unitList: List<UnitItem>) {
        this.unitList.clear()
        this.unitList.addAll(unitList)
        updateRecyclerViewItem()
    }

    override fun showMessage(id: Int) {
        ToastUtil.getInstance().makeShort(id)
    }

    override fun showMessage(message: String) {
        ToastUtil.getInstance().makeShort(message)
    }

    private fun initBottomSheet() {
        val maxHeight = (getWindowHeight() * BOTTOM_SHEET_SIZE_SHOW_PERCENT).toInt()
        behavior = BottomSheetBehavior.from(bottomSheetCardView)
        behavior.peekHeight = maxHeight
        val layoutParams = bottomSheetCardView.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight().toInt()
        bottomSheetCardView.layoutParams = layoutParams
        behavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN, BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
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
        unitBottomSheetAdapter = UnitBottomSheetAdapter(searchUnitList, this)
        unitBottomSheetBehavior = UnitBottomSheetAdapter(unitList, this)
        koreanIndexerRecyclerView.setKeywordList(convertUnitListToKeyList(unitList))
        koreanIndexerRecyclerView.layoutManager = GridLayoutManager(this, 2)
        koreanIndexerRecyclerView.setOnItemClickListener { i ->
            kotlin.run {
                if (i < 0) return@run
                unitSelectTextView.text = searchUnitList[i].unitName
                selectUnit = searchUnitList[i]
                behavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
        koreanIndexerRecyclerView.adapter = unitBottomSheetAdapter
        updateSection()
    }

    private fun updateRecyclerViewItem() {
        var searchText: String = inputEditText.text.toString()
        searchUnitList.clear()
        if (searchText.isEmpty()) {
            searchUnitList.addAll(unitList)
        } else {
            for (i in unitList.indices) {
                if (unitList[i].unitName.contains(searchText)) {
                    searchUnitList.add(unitList[i])

                }
            }
        }
        unitBottomSheetAdapter.notifyDataSetChanged()
        updateSection()
    }

    private fun updateSection() {
        if (isSectionUse) {
            if (inputEditText.text.isNotEmpty())
                koreanIndexerRecyclerView.setUseSection(false)
            else {
                koreanIndexerRecyclerView.setKeywordList(convertUnitListToKeyList(unitList))
                koreanIndexerRecyclerView.setUseSection(true)
            }
        } else {
            koreanIndexerRecyclerView.setUseSection(false)
        }
    }

    private fun getBottomSheetDialogDefaultHeight(): Double {
        return getWindowHeight() * BOTTOM_SHEET_SIZE_EXPENDED_PERCENT
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun convertUnitListToKeyList(unitList: List<UnitItem>): ArrayList<String> {
        val list = arrayListOf<String>()
        for (unit in unitList) {
            list.add(unit.unitName)
        }
        return list
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

    companion object {
        const val BOTTOM_SHEET_SIZE_SHOW_PERCENT = 0.6
        const val BOTTOM_SHEET_SIZE_EXPENDED_PERCENT = 0.9
    }
}
