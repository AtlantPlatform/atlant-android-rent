package io.atlant.rent.dagger2.modules

import dagger.Module
import dagger.Provides
import io.atlant.rent.activities.demo.DemoPresenter
import io.atlant.rent.activities.demo.DemoPresenterImpl
import io.atlant.rent.activities.demo.DemoView

@Module
class DemoActivityModule(private val view: DemoView) {

    @Provides
    fun provideView(): DemoView = view

    @Provides
    fun providePresenter(presenter: DemoPresenterImpl): DemoPresenter = presenter

}
