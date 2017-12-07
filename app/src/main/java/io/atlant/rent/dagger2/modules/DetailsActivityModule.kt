package io.atlant.rent.dagger2.modules

import dagger.Module
import dagger.Provides
import io.atlant.rent.activities.details.DetailsPresenter
import io.atlant.rent.activities.details.DetailsPresenterImpl
import io.atlant.rent.activities.details.DetailsView

@Module
class DetailsActivityModule(private val view: DetailsView) {

    @Provides
    fun provideView(): DetailsView = view

    @Provides
    fun providePresenter(presenter: DetailsPresenterImpl): DetailsPresenter = presenter

}