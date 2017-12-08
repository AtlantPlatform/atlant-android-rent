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
        val appPackageName = "com.frostchein.atlant"
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
