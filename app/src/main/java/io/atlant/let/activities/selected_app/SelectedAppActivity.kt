/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.activities.selected_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import io.atlant.let.R
import io.atlant.let.activities.base.BaseActivity
import io.atlant.let.adapter.SelectedAppPagerAdapter
import io.atlant.let.dagger2.component.AppComponent
import io.atlant.let.dagger2.component.DaggerSelectedAppActivityComponent
import io.atlant.let.dagger2.modules.SelectedAppActivityModule
import io.atlant.let.utils.FontsUtils
import io.atlant.let.view.AnimationUtils.AnimationSelectedAppViewPager
import kotlinx.android.synthetic.main.activity_selected_app.*
import javax.inject.Inject

class SelectedAppActivity : BaseActivity(), SelectedAppView {

    @Inject
    lateinit var presenter: SelectedAppPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar_Gradient)
        setContentView(R.layout.activity_selected_app)
        FontsUtils.toOctarineBold(context, selected_app_name)
        presenter.onCreate()
    }

    public override fun initUI() {

    }

    override fun setupComponent(appComponent: AppComponent) {
        val component = DaggerSelectedAppActivityComponent.builder()
                .appComponent(appComponent)
                .selectedAppActivityModule(SelectedAppActivityModule(this))
                .build()
        component.inject(this)
    }

    public override fun useToolbar(): Boolean = false

    public override fun useSwipeRefresh(): Boolean = false

    override fun setAdapter(adapter: SelectedAppPagerAdapter) {
        selected_app_viewpager.adapter = adapter
        selected_app_viewpager.setPageTransformer(true, AnimationSelectedAppViewPager())
        selected_app_indicator.setViewPagerCircle(selected_app_viewpager)
    }

    override fun startWallet() {
        val appPackageName = "io.atlant.wallet"
        val launchIntent = packageManager.getLaunchIntentForPackage(appPackageName)
        if (launchIntent != null) {
            startActivity(launchIntent)//null pointer check in case package name was not found
        } else {
            val url = "https://play.google.com/store/apps/details?id=" + appPackageName
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    override fun startRentals() {
        startActivityDemo(false)
    }

    override fun startTrade() {
        showMessage(getString(R.string.system_section_in_development))
    }

}
