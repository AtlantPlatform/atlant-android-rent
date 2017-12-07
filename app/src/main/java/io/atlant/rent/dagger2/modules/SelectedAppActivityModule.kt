package io.atlant.rent.dagger2.modules

import dagger.Module
import dagger.Provides
import io.atlant.rent.activities.selected_app.SelectedAppPresenter
import io.atlant.rent.activities.selected_app.SelectedAppPresenterImpl
import io.atlant.rent.activities.selected_app.SelectedAppView

@Module
class SelectedAppActivityModule(private val view: SelectedAppView) {

    @Provides
    fun provideView(): SelectedAppView = view

    @Provides
    fun providePresenter(presenter: SelectedAppPresenterImpl): SelectedAppPresenter = presenter

}