package com.example.edgebar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SlidingPanelViewModel : ViewModel() {
    val isPanelVisible = mutableStateOf(false)

    val sliderValue1 = mutableStateOf(0.5f) // Slider 1 value
    val sliderValue2 = mutableStateOf(0.5f) // Slider 2 value
    val toggleState = mutableStateOf(false) // Toggle switch state

    fun openPanel() {
        isPanelVisible.value = true
    }

    fun closePanel() {
        isPanelVisible.value = false
    }

    // Methods to update slider values
    fun updateSlider1(value: Float) {
        sliderValue1.value = value
    }

    fun updateSlider2(value: Float) {
        sliderValue2.value = value
    }

    // Method to update toggle state
    fun updateToggleState(state: Boolean) {
        toggleState.value = state
    }
}