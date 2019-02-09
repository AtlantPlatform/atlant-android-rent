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
