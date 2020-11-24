package com.example.daggerpracticetest.di.module

import com.example.daggerpracticetest.car.body.Body
import com.example.daggerpracticetest.car.body.wheel.Wheel
import dagger.Module
import dagger.Provides

@Module
abstract class BodyModule {
    companion object {
        @Provides
        fun provideBody(wheel: Wheel, color: String) = Body(wheel, color)
    }
}