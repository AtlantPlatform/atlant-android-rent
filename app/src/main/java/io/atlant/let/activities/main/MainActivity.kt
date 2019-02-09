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

package io.atlant.let.activities.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.atlant.let.R
import io.atlant.let.activities.base.BaseActivity
import io.atlant.let.adapter.RentAdapter
import io.atlant.let.dagger2.component.AppComponent
import io.atlant.let.dagger2.component.DaggerMainActivityComponent
import io.atlant.let.dagger2.modules.MainActivityModule
import io.atlant.let.model.Rent
import io.atlant.let.model.RentCity
import io.atlant.let.view.ToolbarMainView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView, ToolbarMainView.CallBack, RentAdapter.CallBack {

    @Inject
    lateinit var presenter: MainPresenter
    @Inject
    lateinit var toolbarMainView: ToolbarMainView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbarTitle(R.string.app_name)
        setCustomToolbar(toolbarMainView)
        enableScrollToolbar()
        presenter.onCreate()
    }

    override fun initUI() {
        main_bt_filter1.setOnClickListener(onClickFilter)
        main_bt_filter2.setOnClickListener(onClickFilter)
        main_bt_filter3.setOnClickListener(onClickFilter)
        main_bt_filter4.setOnClickListener(onClickFilter)
        main_bt_filter5.setOnClickListener(onClickFilter)
    }

    private val onClickFilter = View.OnClickListener { view ->
        when (view.id) {
            R.id.main_bt_filter1 -> presenter.setCategory(1)
            R.id.main_bt_filter2 -> presenter.setCategory(2)
            R.id.main_bt_filter3 -> presenter.setCategory(3)
            R.id.main_bt_filter4 -> presenter.setCategory(4)
            R.id.main_bt_filter5 -> presenter.setCategory(5)
        }
    }

    override fun setupComponent(appComponent: AppComponent) {
        val component = DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                .mainActivityModule(MainActivityModule(this))
                .build()
        component.inject(this)
    }

    public override fun useToolbar(): Boolean {
        return true
    }

    public override fun useSwipeRefresh(): Boolean {
        return true
    }

    override fun setCategory(numberCategory: Int) {
        val drawableNormal = R.drawable.highlight
        val drawablePress = R.drawable.highlight_bt_rate_category

        main_bt_filter1.setBackgroundResource(drawableNormal)
        main_bt_filter2.setBackgroundResource(drawableNormal)
        main_bt_filter3.setBackgroundResource(drawableNormal)
        main_bt_filter4.setBackgroundResource(drawableNormal)
        main_bt_filter5.setBackgroundResource(drawableNormal)

        when (numberCategory) {
            1 -> main_bt_filter1.setBackgroundResource(drawablePress)
            2 -> main_bt_filter2.setBackgroundResource(drawablePress)
            3 -> main_bt_filter3.setBackgroundResource(drawablePress)
            4 -> main_bt_filter4.setBackgroundResource(drawablePress)
            5 -> main_bt_filter5.setBackgroundResource(drawablePress)
        }
    }

    override fun setToolbar(arrayList: ArrayList<RentCity>) {
        toolbarMainView.setContent(arrayList)
    }

    override fun onRefreshUpdate() {
        presenter.onUpdate()
    }

    override fun onUpdate(rents: ArrayList<Rent>) {
        val adapter = RentAdapter(rents)
        adapter.setCallBack(this)
        main_recycler_view.layoutManager = LinearLayoutManager(context)
        main_recycler_view.adapter = adapter
        main_recycler_view.setHasFixedSize(true)
        main_recycler_view.isNestedScrollingEnabled = false
    }

    override fun startActivity(rent: Rent) {
        startActivityDetails(false, rent)
    }

    override fun onCityClick(pos: Int) {
        presenter.setCity(pos)
    }

    override fun onSelected(pos: Int) {
        presenter.onSelected(pos)
    }

}
