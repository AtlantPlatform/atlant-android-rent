/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.activities.demo

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import io.atlant.let.R
import io.atlant.let.activities.base.BaseActivity
import io.atlant.let.dagger2.component.AppComponent
import io.atlant.let.dagger2.component.DaggerDemoActivityComponent
import io.atlant.let.dagger2.component.DemoActivityComponent
import io.atlant.let.dagger2.modules.DemoActivityModule
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_demo.*
import javax.inject.Inject

class DemoActivity : BaseActivity(), DemoView {

    @Inject
    lateinit var presenter: DemoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar_Gradient)
        setContentView(R.layout.activity_demo)
        presenter.onCreate()
    }

    override val imageView: ImageView
        get() = base_image_fon


    override fun initUI() {
        bt_lets_start.setOnClickListener(onClick)
    }

    private val onClick = View.OnClickListener {
        startActivityMain(false)
    }

    override fun setupComponent(appComponent: AppComponent) {
        val component: DemoActivityComponent? = DaggerDemoActivityComponent.builder()
                .appComponent(appComponent)
                .demoActivityModule(DemoActivityModule(this))
                .build()
        component!!.inject(this)
    }

    override fun useToolbar(): Boolean = true

    override fun useSwipeRefresh(): Boolean = false

}
