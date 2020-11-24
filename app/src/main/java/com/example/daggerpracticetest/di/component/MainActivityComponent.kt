package com.example.daggerpracticetest.di.component

import com.example.daggerpracticetest.MainActivity
import com.example.daggerpracticetest.car.Car
import com.example.daggerpracticetest.di.module.BodyModule
import com.example.daggerpracticetest.di.module.DieselEngineModule
import com.example.daggerpracticetest.di.module.PetrolEngineModule
import com.example.daggerpracticetest.di.module.WheelModule
import com.example.daggerpracticetest.di.scope.MainActivityScope
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named

@MainActivityScope
@Subcomponent(modules=[BodyModule::class, PetrolEngineModule::class, WheelModule::class])
interface MainActivityComponent {

    fun getCar(): Car
    fun inject(activity: MainActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance @Named("hp")hp: Int,
            @BindsInstance @Named("torque")t: Int,
            @BindsInstance color: String): MainActivityComponent
    }
}