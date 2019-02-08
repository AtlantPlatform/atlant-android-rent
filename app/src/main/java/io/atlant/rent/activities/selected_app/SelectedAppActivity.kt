/*
 * Copyright 2017, 2018 Tensigma Ltd.
 *
 * Licensed under the Microsoft Reference Source License (MS-RSL)
 *
 * This license governs use of the accompanying software. If you use the software, you accept this license.
 * If you do not accept the license, do not use the software.
 *
 * 1. Definitions
 * The terms "reproduce," "reproduction," and "distribution" have the same meaning here as under U.S. copyright law.
 * "You" means the licensee of the software.
 * "Your company" means the company you worked for when you downloaded the software.
 * "Reference use" means use of the software within your company as a reference, in read only form, for the sole purposes
 * of debugging your products, maintaining your products, or enhancing the interoperability of your products with the
 * software, and specifically excludes the right to distribute the software outside of your company.
 * "Licensed patents" means any Licensor patent claims which read directly on the software as distributed by the Licensor
 * under this license.
 *
 * 2. Grant of Rights
 * (A) Copyright Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free copyright license to reproduce the software for reference use.
 * (B) Patent Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free patent license under licensed patents for reference use.
 *
 * 3. Limitations
 * (A) No Trademark License- This license does not grant you any rights to use the Licensor’s name, logo, or trademarks.
 * (B) If you begin patent litigation against the Licensor over patents that you think may apply to the software
 * (including a cross-claim or counterclaim in a lawsuit), your license to the software ends automatically.
 * (C) The software is licensed "as-is." You bear the risk of using it. The Licensor gives no express warranties,
 * guarantees or conditions. You may have additional consumer rights under your local laws which this license cannot
 * change. To the extent permitted under your local laws, the Licensor excludes the implied warranties of merchantability,
 * fitness for a particular purpose and non-infringement.
 */

package io.atlant.rent.activities.selected_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import io.atlant.rent.R
import io.atlant.rent.activities.base.BaseActivity
import io.atlant.rent.adapter.SelectedAppPagerAdapter
import io.atlant.rent.dagger2.component.AppComponent
import io.atlant.rent.dagger2.component.DaggerSelectedAppActivityComponent
import io.atlant.rent.dagger2.modules.SelectedAppActivityModule
import io.atlant.rent.utils.FontsUtils
import io.atlant.rent.view.AnimationUtils.AnimationSelectedAppViewPager
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
