package io.atlant.rent.activities.demo

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import io.atlant.rent.R
import io.atlant.rent.activities.base.BaseActivity
import io.atlant.rent.dagger2.component.AppComponent
import io.atlant.rent.dagger2.component.DaggerDemoActivityComponent
import io.atlant.rent.dagger2.component.DemoActivityComponent
import io.atlant.rent.dagger2.modules.DemoActivityModule
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_demo.*
import javax.inject.Inject

class DemoActivity : BaseActivity(), DemoView {

    @Inject
    lateinit var presenter: DemoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
