package com.example.keep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ckgin.keeper.Keeper
import com.ckgin.keeper.KeeperData
import com.ckgin.keeper.datastore
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String = "",
    val age: Int = 0,
) : KeeperData

class MainActivity : ComponentActivity() {

    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val keeper = Keeper(this.datastore)

        setContent {
            val textKey = stringPreferencesKey("text")
            val textFieldState = remember {
                TextFieldState()
            }
            var savedText by remember { mutableStateOf("") }

            LaunchedEffect(Unit) {
                //update state
                val text = keeper.take(textKey)
                textFieldState.setTextAndPlaceCursorAtEnd(text)
                savedText = text

                //save text
                snapshotFlow { textFieldState.text }.debounce(1000)
                    .collect {
                        keeper.keep(textKey, it.toString())
                    }
            }

            LaunchedEffect(Unit) {
                keeper.takeAsFlow(textKey).collect {
                    savedText = it
                }
            }

            Column {
                TextField(state = textFieldState)
                Text(text = savedText)
            }
        }
    }
}