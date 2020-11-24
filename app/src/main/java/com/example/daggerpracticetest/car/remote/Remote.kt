package com.example.daggerpracticetest.car.remote

import android.util.Log
import com.example.daggerpracticetest.car.Car
import javax.inject.Inject

class Remote @Inject constructor() {
    fun setListener(car: Car){
        Log.d("", "remote enabled with car: $car")
    }
}