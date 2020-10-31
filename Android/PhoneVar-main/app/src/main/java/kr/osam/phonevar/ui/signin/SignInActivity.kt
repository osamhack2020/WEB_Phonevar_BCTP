package kr.osam.phonevar.ui.signin

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import kr.osam.core.activity.ActivityBase
import kr.osam.core.toast.ToastUtil
import kr.osam.phonevar.Injection
import kr.osam.phonevar.R
import kr.osam.phonevar.ui.main.MainActivity
import kr.osam.phonevar.ui.signin.presenter.SignInContract
import kr.osam.phonevar.ui.signin.presenter.SignInPresenter
import kr.osam.phonevar.util.UniqueID
import kr.osam.phonevar.util.setupActionBar

class SignInActivity : ActivityBase(), SignInContract.View {
    private lateinit var nameEditText: EditText
    private lateinit var serviceNumberEditText: EditText
    private lateinit var registerButton: Button


    override lateinit var presenter: SignInContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.register)
        }

        initView()

        SignInPresenter(
            Injection.provideUserRepository(applicationContext),
            this,
            Injection.provideSchedulerProvider()
        )

    }

    private fun initView() {
        nameEditText = findViewById(R.id.name_edit_text)
        serviceNumberEditText = findViewById(R.id.service_number_edit_text)
        registerButton = findViewById(R.id.register_button)
        registerButton.setOnClickListener { register() }
        serviceNumberEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                register()
            return@setOnEditorActionListener true
        }
    }

    private fun register() {
        val name = nameEditText.text.toString().trim()
        val serviceNumber = serviceNumberEditText.text.toString().trim()
        if (name.isEmpty() && serviceNumber.isEmpty()) {
            ToastUtil.getInstance().makeShort(getString(R.string.input_name_service_number_warning))
        }
        presenter.register(UniqueID.getUniqueID(this), name, serviceNumber)
    }

    override fun showLoading() {
        showProgressDialog(R.string.loading)
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun goToMainActivity() {
        Intent(this@SignInActivity, MainActivity::class.java).apply {
            finish()
            startActivity(this)
        }
    }

    override fun showMessage(id: Int) {
        ToastUtil.getInstance().makeShort(id)
    }

    override fun showMessage(message: String) {
        ToastUtil.getInstance().makeShort(message)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }
}
