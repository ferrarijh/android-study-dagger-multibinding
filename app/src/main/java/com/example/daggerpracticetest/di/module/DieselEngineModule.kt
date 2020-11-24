package com.example.daggerpracticetest.di.module

import com.example.daggerpracticetest.car.engine.DieselEngine
import com.example.daggerpracticetest.car.engine.Engine
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class DieselEngineModule {
    companion object {
        @Provides
        fun provideEngine(e: DieselEngine): Engine = e
    }
}