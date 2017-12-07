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
