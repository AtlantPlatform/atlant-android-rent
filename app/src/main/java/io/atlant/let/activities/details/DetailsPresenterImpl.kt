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

package io.atlant.let.activities.details

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.support.v4.view.ViewPager
import android.support.v4.widget.NestedScrollView
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import io.atlant.let.R
import io.atlant.let.activities.base.BasePresenter
import io.atlant.let.model.Rent
import io.atlant.let.utils.DimensUtils
import io.atlant.let.utils.ScreenUtils
import java.util.*
import javax.inject.Inject

class DetailsPresenterImpl
@Inject
internal constructor(private val view: DetailsView) : DetailsPresenter, BasePresenter {
    private var context: Context? = null
    private var scrollView: NestedScrollView? = null
    private var viewPager: ViewPager? = null
    private var linearViewPager: LinearLayout? = null

    private var y1: Float = 0.toFloat()
    private var y2: Float = 0.toFloat()
    private var timeStart: Long = 0
    private var timeEnd: Long = 0

    override fun onCreate(rent: Rent) {
        context = view.context

        view.setName(rent.name!!)
        view.setUrlImages(ArrayList(Arrays.asList(*rent.imageUrl!!)))
        view.setPrice("$" + " " + rent.priceDollars.toString())
        view.setHowDay(rent.howDay!!)
        view.setDescription(rent.description!!)
        view.setLikes(rent.numberLike)
        view.setCoordinates(40.742785, -73.11457)

        view.setAlphaFooter(0f)

        view.setAddress(rent.country + ", " + rent.city)
        val rooms = context!!.getString(R.string.rent_main_rooms)
        view.setRooms(rent.numberRooms.toString() + " " + rooms)
        val beds = context!!.getString(R.string.rent_main_beds)
        view.setBeds(rent.numberBeds.toString() + " " + beds)
        val bath = context!!.getString(R.string.rent_main_bath)
        view.setBath(rent.numberBath.toString() + " " + bath)
        val guests = context!!.getString(R.string.rent_main_guests)
        view.setGuests("1-" + rent.numberMaxGuests.toString() + " " + guests)

        val amenities = rent.amenities

        if (!amenities!!.isElevator) {
            view.removeAmenitiesElevator()
        }

        if (!amenities.isWifi) {
            view.removeAmenitiesWiFi()
        }

        if (!amenities.isKitchen) {
            view.removeAmenitiesKitchen()
        }

        if (!amenities.isTv) {
            view.removeAmenitiesTV()
        }

        viewPager = view.viewPager
        linearViewPager = view.linearViewPager
        scrollView = view.nestedScrollView
        scrollView!!.isVerticalScrollBarEnabled = false
        sizeViewPager(ScreenUtils.getHeight(context!!) - ScreenUtils.getStatusBarHeight(context!!))
        scrollView!!.setOnTouchListener { view, motionEvent ->
            if (motionEvent.actionMasked == MotionEvent.ACTION_UP) {
                y2 = motionEvent.y
                timeEnd = System.currentTimeMillis()

                if (timeEnd - timeStart < 200 && Math.abs(y2 - y1) > 100) {
                    startAnimationDown()
                }
            }
            true
        }

        scrollView!!.viewTreeObserver.addOnScrollChangedListener {
            var alpha = (scrollView!!.scrollY - view.appBarLayout.height).toFloat()
            if (alpha > 255) {
                alpha = 255f
            }

            if (alpha < 0) {
                alpha = 0f
            }
            val paint = Paint()
            paint.color = Color.parseColor("#03354f")
            paint.alpha = alpha.toInt()

            view.appBarLayout.setBackgroundColor(paint.color)
        }

        viewPager!!.setOnTouchListener { view, motionEvent ->
            if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                y1 = motionEvent.y
                timeStart = System.currentTimeMillis()
            }
            false
        }
    }

    override fun startAnimationDown() {
        view.startAnimation()
        val time = 1000
        val animationInterpolator = AccelerateDecelerateInterpolator()

        val dp = 200
        val px = DimensUtils.dpToPx(context!!, dp.toFloat())
        val animSizeViewPager = ValueAnimator.ofInt(viewPager!!.measuredHeight, px)
        animSizeViewPager.interpolator = animationInterpolator
        animSizeViewPager.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            sizeViewPager(`val`)
            if (`val` == px) {
                scrollView!!.setOnTouchListener(null)
                scrollView!!.isVerticalScrollBarEnabled = true
                scrollView!!.setPadding(0, 0, 0, view.sizeFooter)
            }
        }
        animSizeViewPager.duration = time.toLong()
        animSizeViewPager.start()

        val animSizeLinearViewPager = ValueAnimator.ofInt(linearViewPager!!.measuredHeight, px)
        animSizeLinearViewPager.interpolator = animationInterpolator
        animSizeLinearViewPager.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            linearViewPager!!.layoutParams.height = `val`
        }
        animSizeLinearViewPager.duration = time.toLong()
        animSizeLinearViewPager.start()

        val animAlpha = ValueAnimator.ofFloat(0.0f, 1.0f)
        val currentAlphaPagerView = viewPager!!.alpha
        animAlpha.interpolator = animationInterpolator
        animAlpha.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Float
            view.setAlphaFooter(`val`)
            linearViewPager!!.alpha = 1.0f - `val`
            viewPager!!.alpha = currentAlphaPagerView + `val` / 2
        }
        animAlpha.duration = time.toLong()
        animAlpha.start()
    }

    private fun sizeViewPager(pxHeight: Int) {
        val params = viewPager!!.layoutParams
        params.height = pxHeight
        viewPager!!.layoutParams = params
    }
}
