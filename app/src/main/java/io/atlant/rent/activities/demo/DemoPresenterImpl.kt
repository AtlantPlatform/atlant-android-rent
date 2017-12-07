package io.atlant.rent.activities.demo

import com.squareup.picasso.Picasso
import io.atlant.rent.activities.base.BasePresenter
import io.atlant.rent.utils.ScreenUtils
import javax.inject.Inject

class DemoPresenterImpl
@Inject
internal constructor(private val view: DemoView) : DemoPresenter, BasePresenter {

    override fun onCreate() {
        Picasso.with(view.context).load("https://rent.atlant.io/assets/images/background.jpg")
                .resize(ScreenUtils.getWidth(view.context), 0).into(view.imageView)
    }

}
