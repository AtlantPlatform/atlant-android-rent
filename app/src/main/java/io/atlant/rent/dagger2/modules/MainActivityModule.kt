package io.atlant.rent.dagger2.modules

import dagger.Module
import dagger.Provides
import io.atlant.rent.activities.main.MainPresenter
import io.atlant.rent.activities.main.MainPresenterImpl
import io.atlant.rent.activities.main.MainView
import io.atlant.rent.view.ToolbarMainView

@Module
class MainActivityModule(private val view: MainView) {

    @Provides
    fun provideView(): MainView = view

    @Provides
    fun providePresenter(presenter: MainPresenterImpl): MainPresenter = presenter

    @Provides
    fun provideToolbar(): ToolbarMainView = ToolbarMainView(view.context)

}