package io.atlant.rent.dagger2.modules

import dagger.Module
import dagger.Provides
import io.atlant.rent.MyApplication
import javax.inject.Singleton

@Module
class AppModule(val myApp: MyApplication) {

    @Provides
    @Singleton
    fun providesApplication(): MyApplication = myApp
}
