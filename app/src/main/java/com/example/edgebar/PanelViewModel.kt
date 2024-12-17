package com.example.edgebar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SlidingPanelViewModel : ViewModel() {
    val isPanelOpen = mutableStateOf(false)

    fun openPanel() {
        isPanelOpen.value = true
    }

    fun closePanel() {
        isPanelOpen.value = false
    }
}