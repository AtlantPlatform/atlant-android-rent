package io.atlant.rent.activities.details

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.support.v4.view.ViewPager
import android.support.v4.widget.NestedScrollView
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.widget.LinearLayout
import io.atlant.rent.R
import io.atlant.rent.activities.base.BasePresenter
import io.atlant.rent.model.Rent
import io.atlant.rent.utils.DimensUtils
import io.atlant.rent.utils.ScreenUtils
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

        val dp = 200
        val px = DimensUtils.dpToPx(context!!, dp.toFloat())
        val animSize = ValueAnimator.ofInt(viewPager!!.measuredHeight, px)
        animSize.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            sizeViewPager(`val`)
            if (`val` == px) {
                scrollView!!.setOnTouchListener(null)
                scrollView!!.isVerticalScrollBarEnabled = true
                scrollView!!.setPadding(0, 0, 0, view.sizeFooter)
            }
        }
        animSize.duration = time.toLong()
        animSize.start()

        val animSize2 = ValueAnimator.ofInt(linearViewPager!!.measuredHeight, 0)
        animSize2.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int

            val params = linearViewPager!!.layoutParams
            params.height = `val`
            linearViewPager!!.layoutParams = params
        }
        animSize2.duration = time.toLong()
        animSize2.start()

        val animAlphaFooter = ValueAnimator.ofFloat(0.0f, 1.0f)
        animAlphaFooter.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Float
            view.setAlphaFooter(`val`)
        }
        animAlphaFooter.duration = time.toLong()
        animAlphaFooter.start()

        val animAlphaViewPager = ValueAnimator.ofFloat(0.5f, 1.0f)
        animAlphaViewPager.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Float
            viewPager!!.alpha = `val`
        }
        animAlphaViewPager.duration = time.toLong()
        animAlphaViewPager.start()

        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                linearViewPager!!.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        alphaAnimation.duration = time.toLong()
        linearViewPager!!.startAnimation(alphaAnimation)

    }

    private fun sizeViewPager(pxHeight: Int) {
        val params = viewPager!!.layoutParams
        params.height = pxHeight
        viewPager!!.layoutParams = params
    }
}
