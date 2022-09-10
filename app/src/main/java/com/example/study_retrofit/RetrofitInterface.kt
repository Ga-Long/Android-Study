package com.example.study_retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitInterface { //api를 관리함
    @GET("api/v2/list_movies.json")
    fun getResult(): Call<MovieAPIResponse> //data를 가지고옴
}