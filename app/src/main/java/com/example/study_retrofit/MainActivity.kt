package com.example.study_retrofit

import android.os.Bundle
import android.view.View.inflate
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.study_retrofit.databinding.ActivityMainBinding.inflate
import com.example.study_retrofit.databinding.ItemMovieBinding.inflate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    //private val binding by lazy { MainActivity.inflate(layoutInflater) }

    private lateinit var viewModel: MainAcitivityVeiwModel
    private lateinit var adapter: MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainAcitivityVeiwModel::class.java]
        adapter = MovieAdapter(viewModel)
        //binding.recyclerView.adapter = adapter //RecyclerView와 Adapter 연결
        //binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //binding.recyclerView.setHasFixedSize(true)//아이템마다 각 높이를 일정하게

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
                        val limit : Int = data.data.limit.toInt() //총 개수
                        for(i: Int in 0 until limit){
                            if(data.data.movies[i].year == "2022"){ // pos: i , title이랑 summary 전달
                                println("title = ${data.data.movies[i].title}")
                                println("summary = ${data.data.movies[i].summary}")
                                println("image = ${data.data.movies[i].medium_cover_image}")
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MovieAPIResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }
}