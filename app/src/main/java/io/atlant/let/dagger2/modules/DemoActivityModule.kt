/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.dagger2.modules

import dagger.Module
import dagger.Provides
import io.atlant.let.activities.demo.DemoPresenter
import io.atlant.let.activities.demo.DemoPresenterImpl
import io.atlant.let.activities.demo.DemoView

@Module
class DemoActivityModule(private val view: DemoView) {

    @Provides
    fun provideView(): DemoView = view

    @Provides
    fun providePresenter(presenter: DemoPresenterImpl): DemoPresenter = presenter

}
