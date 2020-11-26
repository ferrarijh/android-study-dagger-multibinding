# Dagger Multi-Binding with ViewModel

## Demo

<div>
    <img src="https://github.com/ferrarijh/android-study-dagger-multibinding/blob/master/demo/demo.gif" />
</div>

(demo.gif itself does not matter much for dagger multi-binding practice) 

## Why do we need multi-binding?

Consider situation below.

MyViewModel.kt:
```kotlin
class MyViewModel(val service: Service): ViewModel(){
    
    val data = MutableLiveData<Result>()    

    fun fetchResult(){
        data.postValue(service.fetchResult())
    }
}
```

Here Dagger doesn't know how to initialize `MyViewModel` instance, since we let `ViewModelProvider` to work to acquire the reference to the ViewModel instance as below.
```kotlin
val mViewModel by lazy{ ViewModelProvider(requireActivity()).get(MyViewModel::class.java) }
```

After implementing multi-binding, we declare 'factory' instance and inject it `ViewModelProvider`.

```kotlin
@Inject val mFactory: ViewModelFactory
val mViewModel by lazy{ ViewModelProvider(requireActivity(), mFactory).get(MyViewModel::class.java) }
```

Let's see what that means.

### How to do it?
We need `Map` and `Provider`.

When `MyViewModel` instance is requested to `ViewModelProvider` instance the provider should return us the one that matches current scope.
For example from below, (in fragment)
```kotlin
class SomeFragment{
    val viewModel1 = ViewModelProvider(requireActivity(), mFactory).get(MyViewModel::class.java)
    val viewModel2 = ViewModelProvider(requireActivity(), mFactory).get(MyViewModel::class.java)    //scope is activity
    val viewModel3 = ViewModelProvider(this, mFactory).get(MyViewModel::class.java)                 //scope is fragment
    //...
}
```
`viewModel1` and `viewModel2` should be same instance and `viewModel3` should be a different one.

To do this with we let Dagger create a `Map` that maps 'class type'(key) to 'instance'(value) that matches the class type, with the right scope.
For Dagger to choose the one with right scope upon request, we use Dagger's `Provider` class to wrap the ViewModel instance.

1) Create annotation class with `@MapKey` annotation

To let Dagger create the map we need to create special annotation class annotated with `@MapKey` with others.

```kotlin
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
```
(Parameter type is key's type)

2) Tell Dagger which ones should be the map's values with `@IntoMap` with `@Provides` in module class

ViewModelModule.kt:
```kotlin
@Module
abstract class ViewModelModule {
    companion object {
        @Provides
        fun provideViewModelFactory(providerMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelFactory {
            return ViewModelFactory(providerMap)
        }

        @Provides
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        fun provideMainViewModel(service: Service): ViewModel {
            return MainViewModel(Service)
        }

        //add extra ViewModels here like the one above
    }
}
```
Any return value provided by method annotated with `@Provides` and `@IntoMap`
will be put into the map which accepts anything that extends `ViewModel`.
Here the inserted key-value pair's key type is `MainViewModel::class`.

3) Define custom factory class to inject to `ViewModelProvider`

Factory class is responsible for providing the right type of View Model class so we let dagger inject the map into it.

ViewModelFactory.kt:
```kotlin
class ViewModelFactory @Inject constructor(
    //Kotlin compiler invokes error without @JvmSuppressWildcards here
    val providerMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
): ViewModel.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun<T: ViewModel?> create(modelClass: Class<T>): T{
        return providerMap[modelClass]?.get() as T ?: throw RuntimeException("unsupported ViewModel class")
    }
}
```

`Provider<>.get()` returns the wrapped object with the right scope.

Then add `ViewModelModule` to your component class. Done!

## Solved Problems
* In multi-binding, `java.util.Map cannot be provided without without an @Provides-annotated method.`
-- Solved by adding `@JvmSuppressWildcards` in `Map`'s value type as below.

```kotlin
Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
```