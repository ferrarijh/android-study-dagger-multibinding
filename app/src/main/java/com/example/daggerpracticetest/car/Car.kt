package com.example.daggerpracticetest.car

import android.util.Log
import com.example.daggerpracticetest.car.body.Body
import com.example.daggerpracticetest.car.engine.Engine
import com.example.daggerpracticetest.car.remote.Remote
import com.example.daggerpracticetest.di.scope.MainActivityScope
import javax.inject.Inject

@MainActivityScope
class Car @Inject constructor(
    val body: Body,
    val engine: Engine,
    val driver: Driver
){
    fun drive(){
        engine.start()
        Log.d("", "driver '${driver.name}'[${driver.hashCode()}] is driving car [${this.hashCode()}]..")
    }

    @Inject
    fun enableRemote(r: Remote){
        r.setListener(this)
    }
}