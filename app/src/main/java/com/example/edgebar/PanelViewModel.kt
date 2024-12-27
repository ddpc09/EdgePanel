package com.example.edgebar

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SlidingPanelViewModel : ViewModel() {
    val isPanelVisible = mutableStateOf(false)

    val sliderValue1 = mutableFloatStateOf(0.5f) // Slider 1 value
    val sliderValue2 = mutableFloatStateOf(0.5f) // Slider 2 value
    val toggleState1 = mutableStateOf(false) // Toggle switch state
    val toggleState2 = mutableStateOf(false) // Toggle switch state




    fun openPanel() {
        isPanelVisible.value = true
    }

    fun closePanel() {
        isPanelVisible.value = false
    }

//     Methods to update slider values
    fun updateSlider1(value: Float) {
        sliderValue1.floatValue = value
    }

    fun updateSlider2(value: Float) {
        sliderValue2.floatValue = value
    }

    // Method to update toggle state
    fun updateToggleState1(state: Boolean) {
        toggleState1.value = state
    }

    fun updateToggleState2(state: Boolean) {
        toggleState2.value = state
    }






}