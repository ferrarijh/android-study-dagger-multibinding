package com.example.daggerpracticetest.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class FakeService @Inject constructor() {

    suspend fun fetchRandInt(delayTime: Long): Int{
        return withContext(Dispatchers.Default){
            delay(delayTime)
            Random.nextInt(100, 1000)
        }
    }
}