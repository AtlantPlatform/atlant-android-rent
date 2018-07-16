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

package io.atlant.rent.model

import android.os.Parcel
import android.os.Parcelable

class Rent(
        var name: String?,
        var country: String?,
        var city: String?,
        var imageUrl: Array<String>?,
        var numberRooms: Int,
        var numberBeds: Int,
        var numberBath: Int,
        var numberMaxGuests: Int,
        var priceDollars: Int,
        var howDay: String?,
        var description: String?,
        var isGrowth: Boolean,
        var numberLike: Int,
        var amenities: Amenities?) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createStringArray(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readParcelable(Amenities::class.java.classLoader)) {
    }

    class Amenities(var isTv: Boolean, var isElevator: Boolean, var isWifi: Boolean, var isKitchen: Boolean) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readByte() != 0.toByte(),
                parcel.readByte() != 0.toByte(),
                parcel.readByte() != 0.toByte(),
                parcel.readByte() != 0.toByte()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeByte(if (isTv) 1 else 0)
            parcel.writeByte(if (isElevator) 1 else 0)
            parcel.writeByte(if (isWifi) 1 else 0)
            parcel.writeByte(if (isKitchen) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Amenities> {
            override fun createFromParcel(parcel: Parcel): Amenities {
                return Amenities(parcel)
            }

            override fun newArray(size: Int): Array<Amenities?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeString(city)
        parcel.writeStringArray(imageUrl)
        parcel.writeInt(numberRooms)
        parcel.writeInt(numberBeds)
        parcel.writeInt(numberBath)
        parcel.writeInt(numberMaxGuests)
        parcel.writeInt(priceDollars)
        parcel.writeString(howDay)
        parcel.writeString(description)
        parcel.writeByte(if (isGrowth) 1 else 0)
        parcel.writeInt(numberLike)
        parcel.writeParcelable(amenities, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rent> {
        override fun createFromParcel(parcel: Parcel): Rent {
            return Rent(parcel)
        }

        override fun newArray(size: Int): Array<Rent?> {
            return arrayOfNulls(size)
        }
    }

}
