package com.example.daggerpracticetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.daggerpracticetest.application.BaseApplication
import com.example.daggerpracticetest.car.Car
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var car1: Car
    @Inject lateinit var car2: Car

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val component = (application as BaseApplication).appComponent
                .mainActivityComponentFactory
                .create(200, 50, "Indigo")

        component.inject(this)

        car1.drive()
        car2.drive()
    }
}