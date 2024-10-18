package com.novatech.coroutinestutorial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.novatech.coroutinestutorial.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis

data class Person(
    val name: String = "",
    val age: Int = -1
)

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

//        Log.d(TAG, "before run blocking")
//        runBlocking {
//            launch(Dispatchers.IO) {
//                delay(3000L)
//                Log.d(TAG, "finished IO coroutine 1")
//            }
//            launch(Dispatchers.IO) {
//                delay(3000L)
//                Log.d(TAG, "finished IO coroutine 2")
//            }
//            Log.d(TAG, "start run blocking")
//            delay(5000L)
//            Log.d(TAG, "end run blocking")
//        }
//        Log.d(TAG, "after run blocking")


//        val job = GlobalScope.launch(Dispatchers.Default) {
////            repeat(5) {
////                Log.d(TAG, "Coroutine is still working")
////                delay(1000L)
////            }
//
//            Log.d(TAG, "Starting long running calculation...")
//
//            withTimeout(3000L){
//                for(i in 30..80){
//                    if(isActive){
//                        Log.d(TAG, "Result for i = $i: ${fib(i)}")
//                    }
//                }
//            }
//        }

//        runBlocking {
////            job.join()
//
//            delay(2000L)
//            job.cancel()
//            Log.d(TAG, "Canceled job!")
//        }


//        GlobalScope.launch(Dispatchers.IO) {
//            val time = measureTimeMillis {
//                val answer1 = async { networkCall1() }
//                val answer2 = async { networkCall2() }
//
//                Log.d(TAG, "Answer 1 is ${answer1.await()}")
//                Log.d(TAG, "Answer 2 is ${answer2.await()}")
//            }
//            Log.d(TAG, "Request took $time ms")
//        }


//        binding.btnStartActivity.setOnClickListener{
//            lifecycleScope.launch {
//                while (true){
//                    delay(1000L)
//                    Log.d(TAG, "Still running....")
//                }
//            }
//            lifecycleScope.launch {
//                delay(5000L)
//                Intent(this@MainActivity, SecondActivity::class.java).also {
//                    startActivity(it)
//                    finish()
//                }
//            }
//        }


        // firebase
        try {
            val tutorialDocument = Firebase.firestore.collection("coroutines")
                .document("tutorial")
            val peter = Person("Peter", 25)

            GlobalScope.launch(Dispatchers.IO) {
                delay(10000L)

                tutorialDocument.set(peter).await()
                val person = tutorialDocument.get().await().toObject(Person::class.java)
                withContext(Dispatchers.Main) {
                    binding.tvData.text = person.toString()
                }
            }
        } catch (e:Exception){
            Log.e(TAG, e.toString())
        }



    }

    suspend fun networkCall1(): String{
        delay(3000L)
        return "Answer 1"
    }

    suspend fun networkCall2(): String{
        delay(3000L)
        return "Answer 2"
    }


    fun fib(n: Int): Long {
        return if(n==0) 0
        else if(n==1) 1
        else fib(n-1) + fib(n-2)
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