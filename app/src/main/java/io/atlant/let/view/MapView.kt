/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.view

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.Path.Direction
import android.location.Address
import android.location.Geocoder
import android.os.AsyncTask
import android.support.annotation.DrawableRes
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.atlant.let.R
import io.atlant.let.utils.DimensUtils
import java.io.IOException
import java.util.*

class MapView : RelativeLayout, OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private var geocoder: Geocoder? = null
    private var imTransparent: ImageView? = null
    private var drawableIcon = 0
    private var var1 = 0.0
    private var var2 = 0.0
    private var zoom = 10f
    private var callBack: CallBack? = null

    interface CallBack {

        fun getAddress(address: String)
    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MapView)
        try {
            var1 = typedArray.getFloat(R.styleable.MapView_mv_var1, var1.toFloat()).toDouble()
            var2 = typedArray.getFloat(R.styleable.MapView_mv_var2, var2.toFloat()).toDouble()
            zoom = typedArray.getFloat(R.styleable.MapView_mv_zoom, zoom)
            drawableIcon = typedArray.getResourceId(R.styleable.MapView_mv_icon, drawableIcon)
        } finally {
            typedArray.recycle()
        }
        initView(context)
    }

    private fun initView(context: Context) {
        setWillNotDraw(false)
        val view = View.inflate(getContext(), R.layout.view_map, this)
        val activity = context as Activity
        val mapFragment = activity.fragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)
        geocoder = Geocoder(context, Locale.ENGLISH)
        imTransparent = view.findViewById(R.id.map_image_transparent)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        update()
    }


    fun setLatLng(var1: Double, var2: Double) {
        this.var1 = var1
        this.var2 = var2
        update()
    }

    fun setIcon(@DrawableRes drawable: Int) {
        this.drawableIcon = drawable
        update()
    }

    fun disableNestedScrollView(scrollView: NestedScrollView) {
        imTransparent!!.setOnTouchListener { v, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    scrollView.requestDisallowInterceptTouchEvent(true)
                    false
                }
                MotionEvent.ACTION_UP -> {
                    scrollView.requestDisallowInterceptTouchEvent(false)
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    scrollView.requestDisallowInterceptTouchEvent(true)
                    false
                }
                else -> true
            }
        }
    }

    private fun update() {
        if (googleMap != null) {
            val latLng = LatLng(var1, var2)
            if (drawableIcon != 0) {
                val icon = BitmapDescriptorFactory.fromResource(drawableIcon)
                googleMap!!.addMarker(MarkerOptions().position(latLng).icon(icon))
            }
            googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            googleMap!!.animateCamera(CameraUpdateFactory.zoomTo(zoom))
            AsyncTaskAddress().execute()
        }
    }

    override fun onDraw(canvas: Canvas) {
        addTriangleToUp(canvas)
        super.onDraw(canvas)
    }

    private fun addTriangleToUp(canvas: Canvas) {
        val path1 = Path()
        val paint = Paint()
        paint.color = Color.RED

        val x = 32
        val rect = RectF(0f, DimensUtils.dpToPx(context, 16f).toFloat(), this.width.toFloat(), this.height.toFloat())

        path1.moveTo(DimensUtils.dpToPx(context, (16 + x).toFloat()).toFloat(), 0f)
        path1.lineTo(DimensUtils.dpToPx(context, (32 + x).toFloat()).toFloat(), DimensUtils.dpToPx(context, 16f).toFloat())
        path1.lineTo(DimensUtils.dpToPx(context, (0 + x).toFloat()).toFloat(), DimensUtils.dpToPx(context, 16f).toFloat())
        path1.close()

        path1.addRect(rect, Direction.CCW)
        canvas.clipPath(path1)
    }

    private inner class AsyncTaskAddress : AsyncTask<Void, Void, Void>() {

        private var addressList: List<Address>? = null

        override fun doInBackground(vararg voids: Void?): Void? {
            try {
                addressList = geocoder!!.getFromLocation(var1, var2, 1)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            if (callBack != null && addressList != null && addressList!!.size > 0) {
                callBack!!.getAddress(addressList!![0].getAddressLine(0))
            }
        }
    }
}
