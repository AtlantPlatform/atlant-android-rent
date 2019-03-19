/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.dagger2.modules

import dagger.Module
import dagger.Provides
import io.atlant.let.activities.details.DetailsPresenter
import io.atlant.let.activities.details.DetailsPresenterImpl
import io.atlant.let.activities.details.DetailsView

@Module
class DetailsActivityModule(private val view: DetailsView) {

    @Provides
    fun provideView(): DetailsView = view

    @Provides
    fun providePresenter(presenter: DetailsPresenterImpl): DetailsPresenter = presenter

}