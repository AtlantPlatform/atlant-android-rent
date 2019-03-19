/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.dagger2.modules

import dagger.Module
import dagger.Provides
import io.atlant.let.MyApplication
import javax.inject.Singleton

@Module
class AppModule(val myApp: MyApplication) {

    @Provides
    @Singleton
    fun providesApplication(): MyApplication = myApp
}
