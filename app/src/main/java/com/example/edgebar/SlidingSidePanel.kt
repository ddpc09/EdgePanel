package com.example.edgebar

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

//@Composable
//fun SlidingPanel(
//    viewModel: SlidingPanelViewModel = viewModel()
//) {
//    val isPanelOpen by viewModel.isPanelOpen
//    val panelWidth = 250.dp // Define the panel width
//    val panelHeight = 400.dp // Define the panel height
//    val verticalOffset = 100.dp // Distance from the top of the screen
//    val panelWidthPx = with(LocalDensity.current) { panelWidth.toPx() }
//    val panelOffsetX = remember { Animatable(if (isPanelOpen) 0f else -panelWidthPx) }
//    val scope = rememberCoroutineScope()
//
//    // States for sliders and toggle
//    var sliderValue1 by remember { mutableStateOf(0.5f) }
//    var sliderValue2 by remember { mutableStateOf(0.5f) }
//    var toggleState by remember { mutableStateOf(false) }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        // Main content
//        Column(modifier = Modifier.fillMaxSize()) {
//            Text("Main Content", modifier = Modifier.padding(16.dp))
//        }
//
//        // Gesture detection for dragging
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .pointerInput(Unit) {
//                    detectHorizontalDragGestures(
//                        onDragEnd = {
//                            // Snap to open or close depending on drag position
//                            scope.launch {
//                                if (panelOffsetX.value > -panelWidthPx / 2) {
//                                    viewModel.openPanel()
//                                    panelOffsetX.animateTo(0f) // Fully open
//                                } else {
//                                    viewModel.closePanel()
//                                    panelOffsetX.animateTo(-panelWidthPx) // Fully closed
//                                }
//                            }
//                        },
//                        onHorizontalDrag = { change, dragAmount ->
//                            change.consume()
//                            scope.launch {
//                                panelOffsetX.snapTo(
//                                    (panelOffsetX.value + dragAmount).coerceIn(-panelWidthPx, 0f)
//                                )
//                            }
//                        }
//                    )
//                }
//        )
//
//        // Sliding panel with adjusted starting position
//        Box(
//            modifier = Modifier
//                .offset(y = verticalOffset) // Position the panel lower from the top
//                .height(panelHeight) // Reduced height
//                .width(panelWidth)
//                .offset { IntOffset(panelOffsetX.value.roundToInt(), 0) }
//                .background(Color.Gray)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            ) {
//                // First Slider
//                Text(text = "Slider 1", color = Color.White)
//                Slider(
//                    value = sliderValue1,
//                    onValueChange = { sliderValue1 = it },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Second Slider
//                Text(text = "Slider 2", color = Color.White)
//                Slider(
//                    value = sliderValue2,
//                    onValueChange = { sliderValue2 = it },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Toggle Switch
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(text = "Toggle", color = Color.White)
//                    Switch(
//                        checked = toggleState,
//                        onCheckedChange = { toggleState = it }
//                    )
//                }
//            }
//        }
//    }
//}

@Composable
fun SlidingPanel(
    viewModel: SlidingPanelViewModel = viewModel()
) {
    val isPanelOpen by viewModel.isPanelOpen
    val panelWidth = 150.dp // Define the panel width
    val panelHeight = 300.dp // Define the panel height
    val verticalOffset = 100.dp // Distance from the top of the screen
    val panelWidthPx = with(LocalDensity.current) { panelWidth.toPx() }
    val panelOffsetX = remember { Animatable(if (isPanelOpen) 0f else -panelWidthPx) }
    val scope = rememberCoroutineScope()

    // States for sliders and toggle
    var sliderValue1 by remember { mutableStateOf(0.6f) }
    var sliderValue2 by remember { mutableStateOf(0.6f) }
    var toggleState by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content
        Column(modifier = Modifier.fillMaxSize()) {
            Text("Main Content", modifier = Modifier.padding(8.dp))
        }

        // Gesture detection for dragging
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            // Snap to open or close depending on drag position
                            scope.launch {
                                if (panelOffsetX.value > -panelWidthPx / 2) {
                                    viewModel.openPanel()
                                    panelOffsetX.animateTo(0f) // Fully open
                                } else {
                                    viewModel.closePanel()
                                    panelOffsetX.animateTo(-panelWidthPx) // Fully closed
                                }
                            }
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            scope.launch {
                                panelOffsetX.snapTo(
                                    (panelOffsetX.value + dragAmount).coerceIn(-panelWidthPx, 0f)
                                )
                            }
                        }
                    )
                }
        )

        // Sliding panel with adjusted starting position
        Box(
            modifier = Modifier
                .offset(y = verticalOffset) // Position the panel lower from the top
                .height(panelHeight) // Reduced height
                .width(panelWidth)
                .offset { IntOffset(panelOffsetX.value.roundToInt(), 0) }
                .background(Color.Gray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Toggle Switch at the center
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Switch(
                        checked = toggleState,
                        onCheckedChange = { toggleState = it }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Horizontal Sliders
                Row(
                    modifier = Modifier
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // First Vertical Slider
                    Box(
                        modifier = Modifier
                            .height(400.dp) // Increased height for larger slider
                            .width(60.dp) // Increased width for better interaction
                            .graphicsLayer { rotationZ = -90f }
                    ) {
                        Slider(
                            value = sliderValue1,
                            onValueChange = { sliderValue1 = it },
                            modifier = Modifier.fillMaxHeight()
                        )
                    }

                    // Second Vertical Slider
                    Box(
                        modifier = Modifier
                            .height(400.dp) // Increased height for larger slider
                            .width(60.dp) // Increased width for better interaction
                            .graphicsLayer { rotationZ = -90f }
                    ) {
                        Slider(
                            value = sliderValue2,
                            onValueChange = { sliderValue2 = it },
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}
