package com.example.study_retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.study_retrofit.databinding.ItemMovieBinding
import java.text.FieldPosition

class MovieAdapter(private val viewModel: MainAcitivityVeiwModel) :
RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setContents() { //+버튼이나 수정한걸로 text 내용 변경

            //val item = viewModel.getItem(adapterPosition)

            //binding.imageMovie.setImageResource(MainAcitivityVeiwModel.icons[icon] ? : R.drawable.ic_baseline_person_24)
            //binding.movieTitle.text =
            //binding.movieSummary.text =
        }

        //ViewHolder 생성, ViewHolder는 View를 담는 상자
        /* override fun onCreateViewHolder(viewGroup:ViewGroup, viewType:Int):ViewHolder{
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemMovieBinding.inflate(layoutInflater,viewGroup,false)
        return ViewHolder(binding) */
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
/*
    //ViewHolder에 데이터 쓰기
    override fun onBindViewHolder(viewHolder:ViewHolder,position: Int){
        viewHolder.setContents(position)
    }

    override fun getItemCount() = viewModel.items.size


    }*/
}