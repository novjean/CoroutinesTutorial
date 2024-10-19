package com.novatech.coroutinestutorial

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class SecondActivity : AppCompatActivity() {
    private val TAG: String = "SecondActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        GlobalScope.launch(Dispatchers.IO) {
//            val comments = api.getComments().await()
//            for(comment in comments){
//                Log.d(TAG, comment.toString())
//            }

//            val response = api.getComments().awaitResponse()
//            if(response.isSuccessful){
//                for(comment in response.body()!!){
//                    Log.d(TAG, comment.toString())
//                }
//            }

            // API Get method is a suspend function
            val response = api.getComments()
            if(response.isSuccessful){
                for(comment in response.body()!!){
                    Log.d(TAG, comment.toString())
                }
            }
        }

        // this is the old new thread method - inefficient
//        api.getComments().enqueue(object: Callback<List<Comment>> {
//            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
//                Log.e(TAG, "ERROR: $t")
//            }
//
//            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
//                if(response.isSuccessful){
//                    response.body()?.let {
//                        for(comment in it){
//                            Log.d(TAG, comment.toString())
//                        }
//                    }
//                }
//            }
//
//        })


    }
}