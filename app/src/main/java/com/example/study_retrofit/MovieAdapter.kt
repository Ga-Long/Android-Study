package com.example.study_retrofit

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.study_retrofit.databinding.ItemMovieBinding

class MovieAdapter(private val dataSet: ArrayList<List<String>>) :
RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
                val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
    }

    class ViewHolder(private val binding:ItemMovieBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:List<String>){
            binding.movieTitle.text = data[0]
            binding.movieSummary.text= data[1]
            Glide.with(itemView)
                .load(data[2])
                .into(binding.imageMovie)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}