package io.atlant.rent.adapter

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.View
import android.view.ViewGroup
import java.util.*

abstract class BaseAdapterScrollCircular<T>(private val arrayList: ArrayList<T>) : PagerAdapter() {
    private val animation = false
    private var positionChange: Int = 0
    private var positionChangeSuccess: Boolean = false

    init {
        arrayList.add(0, arrayList[arrayList.size - 1])
        arrayList.add(arrayList.size, arrayList[1])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        (container as ViewPager).addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    positionChangeSuccess = true
                    positionChange = position
                }
                if (position == count - 1) {
                    positionChangeSuccess = true
                    positionChange = position
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

                if (positionChange == 0 && positionChangeSuccess) {
                    positionChangeSuccess = false
                    container.setCurrentItem(count - 2, animation)
                }
                if (positionChange == count - 1 && positionChangeSuccess) {
                    positionChangeSuccess = false
                    container.setCurrentItem(1, animation)
                }
            }
        })
        return createView(container, position)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    abstract fun createView(container: ViewGroup, position: Int): View

}
