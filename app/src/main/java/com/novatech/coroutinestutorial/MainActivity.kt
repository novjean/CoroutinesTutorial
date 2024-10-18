package com.novatech.coroutinestutorial

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        GlobalScope.launch {
//            delay(3000L)
//            Log.d(TAG, "Coroutine says hello from thread ${Thread.currentThread().name}")
//        }
//        Log.d(TAG, "Hello from thread ${Thread.currentThread().name}")

        GlobalScope.launch {
            val networkCallAnswer = doNetworkCall()
            Log.d(TAG, networkCallAnswer)

            val networkCallAnswer2 = doNetworkCall2()
            Log.d(TAG, networkCallAnswer2)
        }
    }

    suspend fun doNetworkCall(): String{
        delay(3000L)
        return "This is the answer"
    }

    suspend fun doNetworkCall2(): String{
        delay(3000L)
        return "This is the 2nd answer"
    }
}