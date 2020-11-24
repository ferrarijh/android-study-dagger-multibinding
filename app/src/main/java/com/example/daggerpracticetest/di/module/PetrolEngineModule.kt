package com.example.daggerpracticetest.di.module

import com.example.daggerpracticetest.car.engine.Engine
import com.example.daggerpracticetest.car.engine.PetrolEngine
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class PetrolEngineModule {
    companion object {
        @Provides
        fun provideEngine(e: PetrolEngine): Engine = e
    }
}