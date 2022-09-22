package com.example.study_retrofit

import android.os.Bundle
import android.view.View.inflate
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.study_retrofit.databinding.ActivityMainBinding
import com.example.study_retrofit.databinding.ActivityMainBinding.inflate
import com.example.study_retrofit.databinding.ItemMovieBinding.inflate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainAcitivityVeiwModel
    private lateinit var adapter: MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainAcitivityVeiwModel::class.java]


        callMovie()
    }

    private fun callMovie() {

        RetrofitClient.createRetrofit().getResult().enqueue(object : Callback<MovieAPIResponse> {
            override fun onResponse(
                call: Call<MovieAPIResponse>,
                response: Response<MovieAPIResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) { //데이터를 가지고옴
                        val limit: Int = data.data.limit.toInt() //총 개수
                        for (i: Int in 0 until limit) {
                            if (data.data.movies[i].year == "2022") { // pos: i , title이랑 summary 전달
//                                println("title = ${data.data.movies[i].title}")
//                                println("summary = ${data.data.movies[i].summary}")
//                                println("image = ${data.data.movies[i].medium_cover_image}")
                                viewModel.addItem(
                                    listOf(
                                        data.data.movies[i].title,
                                        data.data.movies[i].summary,
                                        data.data.movies[i].medium_cover_image
                                    )
                                )
                            }
                        }
                        adapter = MovieAdapter(viewModel)
                        binding.recyclerView.adapter = adapter //RecyclerView와 Adapter 연결
                        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.recyclerView.setHasFixedSize(true)//아이템마다 각 높이를 일정하게
                    }
                }
            }

            override fun onFailure(call: Call<MovieAPIResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }
}