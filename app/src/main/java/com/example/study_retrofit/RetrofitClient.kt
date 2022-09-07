package com.example.study_retrofit

import okhttp3.internal.Internal.instance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance: RetrofitClient? = null
    private lateinit var retrofitInterface: RetrofitInterface
    private var baseUrl : String = "https://yts.mx/"

    fun createRetrofit(): RetrofitInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) //데이터 파싱 역할자 지정
            .build()
        return retrofit.create(RetrofitInterface::class.java) //retrofit 서비스 객체 생성

    }

}