/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.activities.base

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Handler
import android.support.annotation.LayoutRes
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import io.atlant.let.MyApplication
import io.atlant.let.R
import io.atlant.let.activities.demo.DemoActivity
import io.atlant.let.activities.details.DetailsActivity
import io.atlant.let.activities.main.MainActivity
import io.atlant.let.dagger2.component.AppComponent
import io.atlant.let.event.LogOut
import io.atlant.let.model.Rent
import io.atlant.let.utils.CredentialHolder
import io.atlant.let.utils.FontsUtils
import io.atlant.let.utils.IntentUtils
import kotlinx.android.synthetic.main.activity_base.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override val context: Context
        get() = this

    protected fun setCustomToolbar(toolbar: View) {
        base_custom_toolbar.addView(toolbar)
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val rootLayout = layoutInflater.inflate(R.layout.activity_base, null) as RelativeLayout
        val activityContainer = rootLayout.findViewById<FrameLayout>(R.id.base_activity_content)
        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(rootLayout)

        setupComponent(MyApplication[context].appComponent!!)

        base_toolbar.title = ""
        FontsUtils.toOctarineBold(context, base_toolbar_title)
        setSupportActionBar(base_toolbar!!)
        if (useToolbar()) {
            base_toolbar.visibility = View.VISIBLE
        } else {
            base_toolbar.visibility = View.GONE
        }

        if (useSwipeRefresh()) {
            base_refresh.isEnabled = true
            base_refresh.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
            base_refresh.setOnRefreshListener {
                if (useSwipeRefresh()) {
                    onRefreshUpdate()
                }
            }
        } else {
            base_refresh.isEnabled = false
        }
        initUI()
    }

    public override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    public override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    protected fun setToolbarTitle(resourceString: Int) {
        base_toolbar_title!!.setText(resourceString)
    }

    private fun createIntentWithoutFlags(activityClass: Class<*>): Intent = Intent(this, activityClass)

    private fun startActivity(intent: Intent, requestCode: Int, withFinish: Boolean) {
        startActivityForResult(intent, requestCode)
        if (withFinish) {
            finish()
        }
    }

    protected fun enableScrollToolbar() {
        val p = base_toolbar.layoutParams as AppBarLayout.LayoutParams
        p.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
        base_toolbar.layoutParams = p
    }

    protected fun startActivityDemo(withFinish: Boolean) {
        startActivity(createIntentWithoutFlags(DemoActivity::class.java), REQUEST_CODE_DEMO, withFinish)
    }

    protected fun startActivityMain(withFinish: Boolean) {
        createIntentWithoutFlags(MainActivity::class.java)
        startActivity(createIntentWithoutFlags(MainActivity::class.java), REQUEST_CODE_MAIN, withFinish)
    }

    protected fun startActivityDetails(withFinish: Boolean, rent: Rent) {
        val intent = createIntentWithoutFlags(DetailsActivity::class.java)
        intent.putExtra(IntentUtils.EXTRA_STRING.RENT, rent)
        startActivity(intent, REQUEST_CODE_DETAILS, withFinish)
    }

    override fun onNoInternetConnection() {
        showMessage(getString(R.string.system_no_internet_connection))
    }

    override fun onRefreshStart() {
        Handler().postDelayed({
            if (!base_refresh.isRefreshing) {
                base_refresh.isRefreshing = true
            }
        }, 10)
    }

    override fun onRefreshUpdate() {

    }

    override fun onRefreshComplete() {
        Handler().postDelayed({
            if (base_refresh.isRefreshing) {
                base_refresh.isRefreshing = false
            }
        }, 10)
    }

    protected fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    protected abstract fun initUI()

    protected abstract fun setupComponent(appComponent: AppComponent)

    protected abstract fun useToolbar(): Boolean

    protected abstract fun useSwipeRefresh(): Boolean

    @Subscribe
    fun onLogOut(event: LogOut?) {
        if (event!!.isLogOut) {
            CredentialHolder.logOut()
        }
    }

    private companion object {
        val REQUEST_CODE_DEMO = 1
        val REQUEST_CODE_MAIN = 2
        val REQUEST_CODE_DETAILS = 3
    }

}
