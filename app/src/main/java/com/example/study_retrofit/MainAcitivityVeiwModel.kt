package com.example.study_retrofit

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainAcitivityVeiwModel : ViewModel() {
    val getMovieList = MutableLiveData<ArrayList<MovieAPIResponse>>()
    val items = ArrayList<MovieAPIResponse>()

    fun addItem(item: MovieAPIResponse) {
        items.add(item)
        getMovieList.value = items
    }

    fun updateItem(pos: Int, item: MovieAPIResponse) {
        items[pos] = item
        getMovieList.value = items
    }

    fun deleteItem(pos: Int) {
        items.removeAt(pos)
        getMovieList.value = items
    }
}