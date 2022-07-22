package com.example.firebase_test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Item(val name: String, val name2: String)
enum class ItemNotify {
    ADD, UPDATE, DELETE
}


class MyViewModel : ViewModel() {
    val itemsLiveData = MutableLiveData<ArrayList<Item>>()

    private val items = ArrayList<Item>()
    var longClickItem: Int = -1

    var itemNotified: Int = -1
    var itemNotifiedType: ItemNotify = ItemNotify.ADD

    init {
        addItem(Item("study", "Java"))
        addItem(Item("study", "Android"))
        addItem(Item("study", "c++"))
        addItem(Item("exercise", "pilates"))
    }

    fun getItem(pos: Int) = items[pos]
    val itemsSize get() = items.size


    fun addItem(item: Item) {
        itemNotifiedType = ItemNotify.ADD
        itemNotified = itemsSize
        items.add(item)
        itemsLiveData.value = items
    }

    fun updateItem(item: Item, pos: Int) {
        itemNotifiedType = ItemNotify.UPDATE
        itemNotified = pos
        items[pos] = item
        itemsLiveData.value = items

    }

    fun deleteItem(pos: Int) {
        itemNotifiedType = ItemNotify.DELETE
        itemNotified = pos
        items.removeAt(pos)
        itemsLiveData.value = items

    }
}