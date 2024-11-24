package com.example.keep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.keep.ui.theme.KeepTheme
import com.naulian.keeper.Keeper
import com.naulian.keeper.datastore
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val keeper = Keeper(this.datastore)
        runBlocking {
            keeper.keepString("email", "naulian@gmail.com")
        }
        val one = keeper.recallString("email")
        println(one)

        setContent {
            LaunchedEffect(Unit) {
                keeper.recallIntAsFlow("number"){
                    println(it)
                }
            }

            LaunchedEffect(Unit) {
                repeat(5){
                    delay(1000)
                    keeper.keepInt("number", it)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KeepTheme {
        Greeting("Android")
    }
}