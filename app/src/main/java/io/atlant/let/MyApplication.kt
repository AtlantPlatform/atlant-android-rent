/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let

import android.app.Application
import android.content.Context
import io.atlant.let.dagger2.component.AppComponent
import io.atlant.let.dagger2.component.DaggerAppComponent
import io.atlant.let.dagger2.modules.AppModule

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
