package com.example.daggerpracticetest.di.module

import com.example.daggerpracticetest.car.body.wheel.Wheel
import com.example.daggerpracticetest.car.body.wheel.rim.Rim
import dagger.Module
import dagger.Provides

@Module
class WheelModule {
    @Provides
    fun provideWheel() = Wheel(Rim())
}