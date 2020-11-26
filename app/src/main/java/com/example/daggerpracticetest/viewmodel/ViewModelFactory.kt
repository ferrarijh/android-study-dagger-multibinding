package com.example.daggerpracticetest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass


class ViewModelFactory @Inject constructor(
        val providerMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>   //NOT KClass
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return providerMap[modelClass]!!.get() as T
    }
}