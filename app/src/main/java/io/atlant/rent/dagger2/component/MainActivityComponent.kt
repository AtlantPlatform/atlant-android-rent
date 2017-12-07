package io.atlant.rent.dagger2.component

import dagger.Component
import io.atlant.rent.activities.main.MainActivity
import io.atlant.rent.dagger2.ActivityScope
import io.atlant.rent.dagger2.modules.MainActivityModule

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {

    fun inject(activity: MainActivity)
}