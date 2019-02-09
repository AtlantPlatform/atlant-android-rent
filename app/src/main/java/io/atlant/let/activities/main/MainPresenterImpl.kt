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

package io.atlant.let.activities.main


import io.atlant.let.activities.base.BasePresenter
import io.atlant.let.model.Rent
import io.atlant.let.model.Rent.Amenities
import io.atlant.let.model.RentCity
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainPresenterImpl
@Inject
internal constructor(private val view: MainView) : MainPresenter, BasePresenter {

    private var arrayList: ArrayList<Rent>? = null
    private var arrayListCity: ArrayList<RentCity>? = null
    private var numberCategory = 1
    private var numberCity = 0

    override fun onCreate() {
        onUpdateCity()
        setCity(numberCity)
    }

    override fun setCity(numberCity: Int) {
        this.numberCity = numberCity
        setCategory(numberCategory)
    }

    override fun setCategory(numberCategory: Int) {
        this.numberCategory = numberCategory
        view.setCategory(numberCategory)
        onUpdate()
    }

    override fun onUpdateCity() {
        arrayListCity = generateRentCity()
        view.setToolbar(arrayListCity!!)
    }

    override fun onUpdate() {
        view.onRefreshStart()
        arrayList = generateArrayList(numberCity)
        view.onUpdate(arrayList!!)
        view.onRefreshComplete()
    }

    override fun onSelected(pos: Int) {
        view.startActivity(arrayList!![pos])
    }

    companion object {

        //*******************
        // TEMP GENERATION RANDOM DATA FOR DEMO
        private fun generateArrayList(numberCity: Int): ArrayList<Rent> {
            val arrayList = ArrayList<Rent>()

            val n = getRandom(1, 20)
            for (i in 0 until n) {
                arrayList.add(generateRent(numberCity))
            }
            return arrayList
        }

        private fun generateRent(numberCit: Int): Rent {
            return Rent(
                    randomName(),
                    generateRentCity()[numberCit].name,
                    randomAddress(),
                    arrayOf(randomPhoto(), randomPhoto(), randomPhoto(), randomPhoto(), randomPhoto()),
                    getRandom(1, 6), //rooms
                    getRandom(3, 9), //beds
                    getRandom(1, 3), //bath
                    getRandom(2, 9), //max guests
                    getRandom(30, 120), //price dollars
                    "per night",
                    randomDescriptions(),
                    randomBoolean(),
                    getRandom(1, 10000), //like
                    Amenities(randomBoolean(), randomBoolean(), randomBoolean(), randomBoolean()))
        }

        private fun generateRentCity(): ArrayList<RentCity> {
            val arrayList = ArrayList<RentCity>()
            arrayList.add(RentCity("New-York", "https://rent.atlant.io/assets/images/newYork.jpg"))
            arrayList.add(RentCity("San Francisco", "https://rent.atlant.io/assets/images/sanFrancisco.jpg"))
            arrayList.add(RentCity("Los Angeles", "https://rent.atlant.io/assets/images/losAngeles.jpg"))
            arrayList.add(RentCity("Miami", "https://rent.atlant.io/assets/images/miami.jpg"))
            return arrayList
        }

        private fun randomPhoto(): String {
            val endpoint = "https://rent.atlant.io/assets/images/"
            val photo = arrayOf("1_1280x720.jpg", "2_1280x720.jpg", "3_1280x720.jpg", "4_1280x720.jpg", "5_1280x720.jpg", "6_1280x720.jpg", "7_1280x720.jpg", "8_1280x720.jpg", "9_1280x720.jpg", "10_1280x720.jpg", "11_1280x720.jpg", "12_1280x720.jpg", "13_1280x720.jpg", "14_1280x720.jpg", "15_1280x720.jpg", "16_1280x720.jpg", "17_1280x720.jpg", "18_1280x720.jpg", "19_1280x720.jpg", "20_1280x720.jpg")
            return endpoint + photo[getRandom(0, 19)]
        }

        private fun randomDescriptions(): String {
            val descriptions = arrayOf("A completely furnished room available for lease. I favor long term rentals, but am willing to settle for shorter stays as well", "A lovely spacious garden flat. 2 double bedrooms, open arrangement living space, large rooms and an exquisite conservatory. Located close to the railroad terminal and a number of bus stops. Several shops nearby in addition to nice local diners.", "This is an amazing studio condo made of brick that has a genuine city feeling. This studio has a recently redesigned lavatory and kitchen with stainless steel appliances. The space fits four people and is halfway situated on a calm road. This unit comes with cable TV and wi-fi. Just minutes away from public transportation. Also, minutes from three bus routes. Come and explore the appeal of the area.", "The place is located in the heart of the city center, near to parks and commercial areas. The room is a double shared room fully furnished with two king-sized beds (1 available), a desk, double wardrobe, and an air conditioner.")
            val n = getRandom(0, descriptions.size - 1)
            return descriptions[n]
        }

        private fun randomName(): String {
            val name = arrayOf("Chamberino", "Groton", "Rosine", "Nettie", "Shasta", "Iberia", "Harviell", "Titanic", "Wilmington", "Spokane")
            val n = getRandom(0, name.size - 1)
            return name[n]
        }

        private fun randomAddress(): String {
            val address = arrayOf("466 Grace Court, 1943", "121 Village Road, 9366", "321 Orange Street, 9805", "616 Flatbush Avenue, 9621", "698 Murdock Court, 1489", "425 Dooley Street, 1327", "263 Centre Street, 2305", "237 Imlay Street, 8189", "946 Bogart Street, 2418", "946 Bogart Street, 2418", "689 Waldorf Court, 1760", "265 Balfour Place, 506", "352 Debevoise Street, 9511")
            val n = getRandom(0, address.size - 1)
            return address[n]
        }

        private fun randomBoolean(): Boolean {
            return getRandom(0, 1) == 1
        }

        private fun getRandom(min: Int, max: Int): Int {
            if (random == null) {
                random = Random()
            }
            return random!!.nextInt(max - min + 1) + min
        }

        private var random: Random? = null
    }

}
