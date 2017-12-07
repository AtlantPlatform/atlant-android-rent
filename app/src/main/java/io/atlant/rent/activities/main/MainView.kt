package io.atlant.rent.activities.main

import io.atlant.rent.activities.base.BaseView
import io.atlant.rent.model.Rent
import io.atlant.rent.model.RentCity
import java.util.ArrayList

interface MainView : BaseView {

    fun setCategory(numberCategory: Int)

    fun setToolbar(arrayList: ArrayList<RentCity>)

    fun onUpdate(rents: ArrayList<Rent>)

    fun startActivity(rent: Rent)

}
