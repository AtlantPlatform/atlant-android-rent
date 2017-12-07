package io.atlant.rent.activities.details

import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewPager
import android.support.v4.widget.NestedScrollView
import android.widget.LinearLayout
import io.atlant.rent.activities.base.BaseView
import java.util.ArrayList

interface DetailsView : BaseView {

    val sizeFooter: Int

    val viewPager: ViewPager

    val linearViewPager: LinearLayout

    val nestedScrollView: NestedScrollView

    val appBarLayout: AppBarLayout

    fun setName(name: String)

    fun setAddress(address: String)

    fun setRooms(text: String)

    fun setGuests(text: String)

    fun setBeds(text: String)

    fun setBath(text: String)

    fun setLikes(number: Int)

    fun setUrlImages(arrayListURL: ArrayList<String>)

    fun setHowDay(text: String)

    fun setPrice(price: String)

    fun setCoordinates(var1: Double, var2: Double)

    fun setAlphaFooter(alpha: Float)

    fun setDescription(text: String)

    fun startAnimation()

    fun removeAmenitiesTV()

    fun removeAmenitiesElevator()

    fun removeAmenitiesWiFi()

    fun removeAmenitiesKitchen()

}
