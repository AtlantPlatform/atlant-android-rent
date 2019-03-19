/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.activities.details

import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewPager
import android.support.v4.widget.NestedScrollView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import io.atlant.let.R
import io.atlant.let.activities.base.BaseActivity
import io.atlant.let.adapter.RentImagePagerAdapter
import io.atlant.let.dagger2.component.AppComponent
import io.atlant.let.dagger2.component.DaggerDetailsActivityComponent
import io.atlant.let.dagger2.modules.DetailsActivityModule
import io.atlant.let.model.Rent
import io.atlant.let.utils.DialogUtils
import io.atlant.let.utils.DimensUtils
import io.atlant.let.utils.IntentUtils.EXTRA_STRING
import io.atlant.let.utils.ScrollUtils
import io.atlant.let.view.MapView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_content.*
import java.util.*
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsView, MapView.CallBack {

    override val sizeFooter: Int get() = detail_view_footer.height
    override val viewPager: ViewPager get() = detail_viewpager
    override val linearViewPager: LinearLayout get() = detail_viewpager_linear
    override val nestedScrollView: NestedScrollView get() = detail_nested_scroll_view
    override val appBarLayout: AppBarLayout get() = base_appbar

    @Inject
    lateinit var presenter: DetailsPresenter
    private val dialog = DialogUtils(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setToolbarTitle(R.string.app_name)
        dialog.createDialogRentBookIt(getString(R.string.rent_detail_dialog_message), null)
        presenter.onCreate(intent.getParcelableExtra<Parcelable>(EXTRA_STRING.RENT) as Rent)
    }

    public override fun initUI() {
        val params = base_refresh.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = null
        val handler = Handler()
        handler.postDelayed({
            if (ScrollUtils.canScroll(detail_horizontal_scroll_view)) {
                detail_horizontal_scroll_view_linear.orientation = LinearLayout.VERTICAL
            }
        }, 1)
        detail_content_map_view.disableNestedScrollView(nestedScrollView)
        detail_content_map_view.setCallBack(this)

        detail_book_it.setOnClickListener(onClickBookIt)
        detail_bt_down.setOnClickListener(onClickStartDownAnimation)
    }

    override fun setupComponent(appComponent: AppComponent) {
        val component = DaggerDetailsActivityComponent.builder()
                .appComponent(appComponent)
                .detailsActivityModule(DetailsActivityModule(this))
                .build()
        component.inject(this)
    }

    public override fun useToolbar(): Boolean = true

    public override fun useSwipeRefresh(): Boolean = false

    override fun setName(name: String) {
        detail_name_text.text = name
        detail_content_name_text.text = name
    }

    override fun setAddress(address: String) {
        detail_address_text.text = address
        detail_content_address_text.text = address
    }

    override fun setRooms(text: String) {
        detail_rooms_text.text = text
        detail_content_rooms_text.text = text
    }

    override fun setGuests(text: String) {
        detail_content_guests_text.text = text
    }

    override fun setBeds(text: String) {
        detail_beds_text.text = text
        detail_content_beds_text.text = text
    }

    override fun setBath(text: String) {
        detail_content_bath.text = text
    }

    override fun setLikes(number: Int) {
        detail_like_view.like = number
        detail_content_like_view.like = number
    }

    override fun setUrlImages(arrayListURL: ArrayList<String>) {
        detail_viewpager.adapter = RentImagePagerAdapter(context, arrayListURL)
        detail_viewpager.pageMargin = DimensUtils.dpToPx(context, 4f)
        detail_content_indicator.setViewPager(viewPager)
    }

    override fun setHowDay(text: String) {
        detail_how_day_text.text = text
    }

    override fun setPrice(price: String) {
        detail_price_text.text = price
    }

    override fun setCoordinates(var1: Double, var2: Double) {
        detail_content_map_view.setLatLng(var1, var2)
    }

    override fun setAlphaFooter(alpha: Float) {
        detail_view_footer.alpha = alpha
    }

    override fun setDescription(text: String) {
        detail_content_description_text.text = text
    }

    private val onClickBookIt = View.OnClickListener {
        dialog.showDialogFullSize(1.0f)
    }

    private val onClickStartDownAnimation = View.OnClickListener {
        presenter.startAnimationDown()
    }

    override fun startAnimation() {
        detail_content_like_view!!.like = detail_like_view!!.like
    }

    override fun removeAmenitiesTV() {
        (detail_content_amenities_tv_linear!!.parent as ViewGroup).removeView(detail_content_amenities_tv_linear)
    }

    override fun removeAmenitiesElevator() {
        (detail_content_amenities_elevator_linear!!.parent as ViewGroup).removeView(detail_content_amenities_elevator_linear)
    }

    override fun removeAmenitiesWiFi() {
        (detail_content_amenities_wifi_linear!!.parent as ViewGroup).removeView(detail_content_amenities_wifi_linear)
    }

    override fun removeAmenitiesKitchen() {
        (detail_content_amenities_kitchen_linear.parent as ViewGroup).removeView(detail_content_amenities_kitchen_linear)
    }

    override fun getAddress(address: String) {
        detail_content_location_address.text = address
        //temp
        detail_content_location_address.text = detail_content_address_text.text
    }
}
