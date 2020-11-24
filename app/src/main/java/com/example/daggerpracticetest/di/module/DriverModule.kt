package com.example.daggerpracticetest.di.module

import com.example.daggerpracticetest.car.Driver
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class DriverModule {
    companion object{
        @Singleton
        @Provides
        fun provideDriver(@Named("driver") name: String) = Driver(name)
    }
}