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
val mViewModel by lazy{ ViewModelProvider(requireActivity()).get(MyViewModel::class) }
```

After implementing multi-binding, we declare 'factory' instance and inject it to Dagger framework as below.

```kotlin
@Inject val mFactory: ViewModelFactory
val mViewModel by lazy{ ViewModelProvider(requireActivity(), mFactory).get(MyViewModel::class) }
```

### Provider



### ViewModel.Factory

ViewModelFactory.kt:
```kotlin
class ViewModelFactory @Inject constructor(
    val providerMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
): ViewModel.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun<T: ViewModel?> create(modelClass: Class<T>): T{
        return providerMap[modelClass]?.get() as T
    }
}
```

## Solved Problems
* In multi-binding, `java.util.Map cannot be provided without without an @Provides-annotated method.`
-- Solved by adding `@JvmSuppressWildcards` in `Map`'s value type as below.

```kotlin
Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
```