package com.novatech.coroutinestutorial

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.novatech.coroutinestutorial.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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

//        GlobalScope.launch {
//            val networkCallAnswer = doNetworkCall()
//            Log.d(TAG, networkCallAnswer)
//
//            val networkCallAnswer2 = doNetworkCall2()
//            Log.d(TAG, networkCallAnswer2)
//        }

//        GlobalScope.launch(newSingleThreadContext("MyThread")) {
//
//        }

//        GlobalScope.launch(Dispatchers.IO) {
//            Log.d(TAG, "Starting coroutine in thread ${Thread.currentThread().name}")
//            val answer = doNetworkCall()
//            withContext(Dispatchers.Main) {
//                Log.d(TAG, "Starting coroutine in thread ${Thread.currentThread().name}")
//                binding.tvDummy.text = answer
//            }
//        }

        Log.d(TAG, "before run blocking")
        runBlocking {
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "finished IO coroutine 1")
            }
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "finished IO coroutine 2")
            }
            Log.d(TAG, "start run blocking")
            delay(5000L)
            Log.d(TAG, "end run blocking")
        }
        Log.d(TAG, "after run blocking")
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