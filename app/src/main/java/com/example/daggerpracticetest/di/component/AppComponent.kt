package com.example.daggerpracticetest.di.component

import com.example.daggerpracticetest.di.module.DriverModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules=[DriverModule::class])
interface AppComponent {
    val mainActivityComponentFactory: MainActivityComponent.Factory

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance @Named("driver") driverName: String): AppComponent
    }
}