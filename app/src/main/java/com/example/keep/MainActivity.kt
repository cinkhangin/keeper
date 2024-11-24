package com.example.keep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.naulian.keeper.Keeper
import com.naulian.keeper.datastore
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String = "",
    val age: Int = 0,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val keeper = Keeper(this.datastore)

        val user = User(name = "Naulian", age = 25)
        runBlocking {
            keeper.keepString("email", "naulian@gmail.com")
            keeper.keep("user", user)
        }
        val one = keeper.recallString("email")
        val savedUser = keeper.recall("user", User())
        println(savedUser)
        println(one)

        setContent {
            LaunchedEffect(Unit) {
                keeper.recallIntAsFlow("number") {
                    println(it)
                }
            }

            LaunchedEffect(Unit) {
                repeat(5) {
                    delay(1000)
                    keeper.keepInt("number", it)
                }
            }
        }
    }
}