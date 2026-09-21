@file:Suppress("LocalVariableName")

package com.example.keep

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ckgin.keeper.datastore
import com.ckgin.keeper.keep
import com.ckgin.keeper.take
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        runBlocking {
            val keeper = this@MainActivity.datastore
            val keeperKey = stringPreferencesKey("keeper_key")

            val TAG = this@MainActivity::class.simpleName
            keeper.keep(keeperKey, "keep")
            Log.i(TAG, "keeping: $keeperKey")

            val keeperValue = keeper.take(keeperKey, "")
            Log.i(TAG, "reading: $keeperValue")
        }
    }
}