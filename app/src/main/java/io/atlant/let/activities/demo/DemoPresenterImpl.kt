/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.activities.demo

import com.squareup.picasso.Picasso
import io.atlant.let.activities.base.BasePresenter
import io.atlant.let.utils.ScreenUtils
import javax.inject.Inject

class DemoPresenterImpl
@Inject
internal constructor(private val view: DemoView) : DemoPresenter, BasePresenter {

    override fun onCreate() {
        Picasso.with(view.context).load("https://rent.atlant.io/assets/images/background.jpg")
                .resize(ScreenUtils.getWidth(view.context), 0).into(view.imageView)
    }

}
