package com.example.daggerpracticetest.di.module

import androidx.lifecycle.ViewModel
import com.example.daggerpracticetest.service.FakeService
import com.example.daggerpracticetest.viewmodel.MainViewModel
import com.example.daggerpracticetest.viewmodel.ViewModelFactory
import com.example.daggerpracticetest.viewmodel.ViewModelKey
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    companion object {
        @JvmStatic
        @Provides
        fun provideViewModelFactory(providerMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelFactory {
            return ViewModelFactory(providerMap)
        }

        @JvmStatic
        @Provides
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        fun provideMainViewModel(fakeService: FakeService): ViewModel {
            return MainViewModel(fakeService)
        }

        //add extra ViewModels here like the one above
    }
}