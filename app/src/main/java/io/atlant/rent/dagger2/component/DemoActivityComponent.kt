package io.atlant.rent.dagger2.component

import dagger.Component
import io.atlant.rent.activities.demo.DemoActivity
import io.atlant.rent.dagger2.ActivityScope
import io.atlant.rent.dagger2.modules.DemoActivityModule

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(DemoActivityModule::class))
interface DemoActivityComponent {

    fun inject(activity: DemoActivity)
}

