package io.atlant.rent.activities.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.atlant.rent.R
import io.atlant.rent.activities.base.BaseActivity
import io.atlant.rent.adapter.RentAdapter
import io.atlant.rent.dagger2.component.AppComponent
import io.atlant.rent.dagger2.component.DaggerMainActivityComponent
import io.atlant.rent.dagger2.modules.MainActivityModule
import io.atlant.rent.model.Rent
import io.atlant.rent.model.RentCity
import io.atlant.rent.view.ToolbarMainView
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
