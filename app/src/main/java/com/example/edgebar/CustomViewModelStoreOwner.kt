package com.example.edgebar

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class CustomViewModelStoreOwner : ViewModelStoreOwner {
    private val store = ViewModelStore()

    override val viewModelStore: ViewModelStore
        get() = store

    fun clear() {
        store.clear()
    }
}
