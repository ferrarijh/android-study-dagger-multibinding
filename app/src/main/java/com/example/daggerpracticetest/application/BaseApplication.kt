package com.example.daggerpracticetest.application

import android.app.Application
import com.example.daggerpracticetest.di.component.AppComponent
import com.example.daggerpracticetest.di.component.DaggerAppComponent

class BaseApplication : Application(){
    val appComponent by lazy{ DaggerAppComponent.factory().create("Jonathan")}
}