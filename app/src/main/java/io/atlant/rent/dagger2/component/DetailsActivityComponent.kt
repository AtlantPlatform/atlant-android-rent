package io.atlant.rent.dagger2.component

import dagger.Component
import io.atlant.rent.activities.details.DetailsActivity
import io.atlant.rent.dagger2.ActivityScope
import io.atlant.rent.dagger2.modules.DetailsActivityModule

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(DetailsActivityModule::class))
interface DetailsActivityComponent {

    fun inject(activity: DetailsActivity)
}