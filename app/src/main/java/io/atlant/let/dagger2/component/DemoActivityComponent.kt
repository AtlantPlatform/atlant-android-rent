/*
 * Copyright 2017-2019 Tensigma Ltd. All rights reserved.
 * Use of this source code is governed by Microsoft Reference Source
 * License (MS-RSL) that can be found in the LICENSE file.
 */

package io.atlant.let.dagger2.component

import dagger.Component
import io.atlant.let.activities.demo.DemoActivity
import io.atlant.let.dagger2.ActivityScope
import io.atlant.let.dagger2.modules.DemoActivityModule

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(DemoActivityModule::class))
interface DemoActivityComponent {

    fun inject(activity: DemoActivity)
}

