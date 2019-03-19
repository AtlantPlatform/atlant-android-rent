/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.dagger2.modules

import dagger.Module
import dagger.Provides
import io.atlant.let.activities.selected_app.SelectedAppPresenter
import io.atlant.let.activities.selected_app.SelectedAppPresenterImpl
import io.atlant.let.activities.selected_app.SelectedAppView

@Module
class SelectedAppActivityModule(private val view: SelectedAppView) {

    @Provides
    fun provideView(): SelectedAppView = view

    @Provides
    fun providePresenter(presenter: SelectedAppPresenterImpl): SelectedAppPresenter = presenter

}