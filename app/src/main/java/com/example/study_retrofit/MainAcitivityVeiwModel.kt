package com.example.study_retrofit

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainAcitivityVeiwModel : ViewModel() {
    private val dataSet: ArrayList<List<String>> = arrayListOf()
    private lateinit var adapter: MovieAdapter

    fun addItem(item: List<String>) {
        dataSet.add(item)

    }

    fun updateItem(pos: Int, item: List<String>) {
        dataSet[pos] = item
    }

    fun deleteItem(pos: Int) {
        dataSet.removeAt(pos)
    }

    fun getData(pos: Int): List<String> {
        return dataSet[pos]
    }

    fun getData(): ArrayList<List<String>> {
        return dataSet
    }


}