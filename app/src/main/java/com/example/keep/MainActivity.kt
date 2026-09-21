package com.example.keep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.ckgin.keeper.Keeper
import com.ckgin.keeper.KeeperData
import com.ckgin.keeper.datastore
import kotlinx.coroutines.FlowPreview
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
        Keeper(this.datastore)
    }
}