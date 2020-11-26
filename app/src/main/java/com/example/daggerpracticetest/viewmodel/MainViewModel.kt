package com.example.daggerpracticetest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daggerpracticetest.di.scope.MainActivityScope
import com.example.daggerpracticetest.service.FakeService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@MainActivityScope
class MainViewModel @Inject constructor(
        val fakeService: FakeService
): ViewModel() {
    val num1 = MutableLiveData<Int>()
    val num2 = MutableLiveData<Int>()

    fun fetchRandInt(t: Long, n: Int){
        CoroutineScope(Dispatchers.Main).launch{
            when(n) {
                1 -> num1.value = fakeService.fetchRandInt(t)
                2 -> num2.value = fakeService.fetchRandInt(t)
            }
        }
    }
}