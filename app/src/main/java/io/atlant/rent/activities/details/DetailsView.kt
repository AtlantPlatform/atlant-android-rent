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
