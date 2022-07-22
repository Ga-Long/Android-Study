package com.example.firebase_test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_test.databinding.ItemLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyAdapter(private val viewModel: MyViewModel) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    val user = Firebase.auth.currentUser

    inner class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            val item = viewModel.getItem(pos)
            binding.title.text = item.name
            binding.subscription.text = item.name2
            binding.writer.text = user?.email ?: " "
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context) //layout inflater을 가지고 옴
        val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false) // 당장 붙히지 않는다 false
        val viewHolder = ViewHolder(binding)
        binding.root.setOnLongClickListener { //롱클릭했을때
            viewModel.longClickItem = viewHolder.adapterPosition
            false //계속해서 처리되게
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount() =
        viewModel.itemsSize


}