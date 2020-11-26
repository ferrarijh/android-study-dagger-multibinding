package com.example.daggerpracticetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.daggerpracticetest.application.BaseApplication
import com.example.daggerpracticetest.car.Car
import com.example.daggerpracticetest.databinding.ActivityMainBinding
import com.example.daggerpracticetest.di.component.AppComponent
import com.example.daggerpracticetest.viewmodel.MainViewModel
import com.example.daggerpracticetest.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var car1: Car
    @Inject lateinit var car2: Car

    private val mViewModel by lazy{ViewModelProvider(this, factory).get(MainViewModel::class.java)}
    @Inject lateinit var factory: ViewModelFactory

    private lateinit var vBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewBinding()

        setCarExample()

        setObserver()
        setBtn()
    }

    private fun setObserver(){
        mViewModel.num1.observe(this){
            vBinding.tv1.text = it.toString()
            vBinding.pb1.visibility = View.GONE
            vBinding.tv1.visibility = View.VISIBLE
        }
        mViewModel.num2.observe(this){
            vBinding.tv2.text = it.toString()
            vBinding.pb2.visibility = View.GONE
            vBinding.tv2.visibility = View.VISIBLE
        }
    }

    private fun setViewBinding(){
        vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vBinding.root)
    }

    private fun setBtn(){
        vBinding.btnFetch.setOnClickListener{
            Log.d("", "btn clicked")
            if (vBinding.tv1.visibility == View.VISIBLE)
                vBinding.tv1.visibility = View.GONE
            if (vBinding.tv2.visibility == View.VISIBLE)
                vBinding.tv2.visibility = View.GONE

            vBinding.pb1.visibility = View.VISIBLE
            vBinding.pb2.visibility = View.VISIBLE
            mViewModel.fetchRandInt(500, 1)
            mViewModel.fetchRandInt(1000, 2)
        }
    }

    private fun setCarExample(){
        val component = (application as BaseApplication).appComponent
            .mainActivityComponentFactory
            .create(200, 50, "Indigo")

        component.inject(this)

        car1.drive()
        car2.drive()
    }
}