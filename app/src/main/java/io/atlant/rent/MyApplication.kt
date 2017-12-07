package io.atlant.rent

import android.app.Application
import android.content.Context
import io.atlant.rent.dagger2.component.AppComponent
import io.atlant.rent.dagger2.component.DaggerAppComponent
import io.atlant.rent.dagger2.modules.AppModule

class MyApplication : Application() {

    var appComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        appComponent!!.inject(this)
    }

    companion object {

        operator fun get(context: Context): MyApplication {
            return context.applicationContext as MyApplication
        }
    }
}
