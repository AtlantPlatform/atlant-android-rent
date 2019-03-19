/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.dagger2.modules

import dagger.Module
import dagger.Provides
import io.atlant.let.activities.main.MainPresenter
import io.atlant.let.activities.main.MainPresenterImpl
import io.atlant.let.activities.main.MainView
import io.atlant.let.view.ToolbarMainView

@Module
class MainActivityModule(private val view: MainView) {

    @Provides
    fun provideView(): MainView = view

    @Provides
    fun providePresenter(presenter: MainPresenterImpl): MainPresenter = presenter

    @Provides
    fun provideToolbar(): ToolbarMainView = ToolbarMainView(view.context)

}