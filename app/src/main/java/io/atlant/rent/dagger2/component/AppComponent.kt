package io.atlant.rent.dagger2.component

import dagger.Component
import io.atlant.rent.MyApplication
import io.atlant.rent.dagger2.modules.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(myApp: MyApplication)

}
