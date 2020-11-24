package com.example.daggerpracticetest.car.engine

import android.util.Log
import javax.inject.Inject
import javax.inject.Named

class DieselEngine @Inject constructor(
    @Named("hp") private val horsepower: Int,
    @Named("torque") private val torque: Int
): Engine {
    override fun start() {
        Log.d("", "starting diesel engine - hp:[$horsepower], torque:[$torque]")
    }
}