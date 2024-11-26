# Keeper
###### An Android Library to make storing preferences easy

### implementation
add the library from maven central like this in `build.gradle.kts` and sync project
```kotlin
dependencies {
    implementation("com.naulian:keeper:0.1.0-alpha01")
}
```

### Creating Keeper Instance
```kotlin
val keeper = Keeper(context.datastore)

//or you can just inject it
class YourClass @Inject constructor(private val keeper : Keeper)
```

### Keeping Data
call inside a coroutine scope
```kotlin
viewModelScope.launch {
    keeper.keepString(key, "value")
    keeper.keepInt(key, 0)
    keeper.keepBoolean(key, true)

    keeper.keep(key, User(name = "John")) //data class need to be annotated with @Serializable
}
```

### Recalling Data
```kotlin
val string = keeper.recallString(key) //default value is ""
val string2 = keeper.recallString(key, "Username") //custom default value
val float = keeper.recallFloat(key, 1f)
```

### Recalling and Collecting
```kotlin
viewModelScope.launch {
    repeat(5){
        keeper.keepInt(key, it)
        delay(1000)
    }
}
viewModelScope.launch {
    keeper.recallIntAsFlow(key) { //collecting
        print(it)
    }
}

//output 01234
```

### That is it!

