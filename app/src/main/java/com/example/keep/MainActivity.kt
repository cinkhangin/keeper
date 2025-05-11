package com.example.keep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.naulian.keeper.Keeper
import com.naulian.keeper.KeeperData
import com.naulian.keeper.datastore
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String = "",
    val age: Int = 0,
) : KeeperData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val keeper = Keeper(this.datastore)
        val keyEmail = stringPreferencesKey("email")
        val keyUser = stringPreferencesKey("user")
        val keyNum = intPreferencesKey("number")

        val user = User(name = "Naulian", age = 25)

        runBlocking {
            keeper.keepString(keyEmail, "email@naulian.com")
            keeper.keep(keyUser, user)
        }

        //val one = keeper.recallString(keyEmail)
        val savedUser = keeper.recall(keyUser, User())

        println(savedUser)

        setContent {
            LaunchedEffect(Unit) {
                keeper.recallIntAsFlow(keyNum) {
                    println(it)
                }
            }

            LaunchedEffect(Unit) {
                repeat(5) {
                    delay(1000)
                    keeper.keepInt(keyNum, it)
                }
            }

            Button(onClick = {}) { }
        }
    }
}