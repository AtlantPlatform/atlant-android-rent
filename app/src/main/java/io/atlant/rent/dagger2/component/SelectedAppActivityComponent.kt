package io.atlant.rent.dagger2.component

import dagger.Component
import io.atlant.rent.activities.selected_app.SelectedAppActivity
import io.atlant.rent.dagger2.ActivityScope
import io.atlant.rent.dagger2.modules.SelectedAppActivityModule

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(SelectedAppActivityModule::class))
interface SelectedAppActivityComponent {

    fun inject(activity: SelectedAppActivity)
}

