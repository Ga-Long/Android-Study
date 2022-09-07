package com.example.study_retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        main()
    }

    private fun main(){

        RetrofitClient.createRetrofit().getResult().enqueue(object : Callback<MovieAPIResponse> {
            override fun onResponse(
                call: Call<MovieAPIResponse>,
                response: Response<MovieAPIResponse>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    if (data != null) {
                        data.data.movies[1].title
                    }
                }
            }

            override fun onFailure(call: Call<MovieAPIResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }
}