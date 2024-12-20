package com.example.edgebar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOut
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
//    val isPanelOpen by viewModel.isPanelVisible
//    val panelWidth = 250.dp // Define the panel width
//    val panelHeight = 400.dp // Define the panel height
//    val verticalOffset = 50.dp // Distance from the top of the screen
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
////        // Main content
////        Column(modifier = Modifier.fillMaxSize()) {
////            Text("Main Content", modifier = Modifier.padding(16.dp))
////        }
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




//@Composable
//fun SlidingPanel(
//    viewModel: SlidingPanelViewModel = viewModel()
////    updatePanelWidth: (Int) -> Unit // Callback to update the service's layout params
//) {
//    val isPanelVisible by viewModel.isPanelVisible
//    val panelWidth = 200.dp // Define the panel width
//    val panelHeight = 500.dp // Define the panel height
//    val verticalOffset = 100.dp // Distance from the top of the screen
//    val panelWidthPx = with(LocalDensity.current) { panelWidth.toPx() }
//    val panelOffsetX = remember { Animatable(if (isPanelVisible) 0f else -panelWidthPx) }
//    val scope = rememberCoroutineScope()
//
////    LaunchedEffect(isPanelVisible) {
////        panelOffsetX.animateTo(if (isPanelVisible) 0f else -panelWidthPx, tween(300))
////        println("Panel visibility changed: $isPanelVisible")
////    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        // Gesture detection for dragging
//        Box(
//            modifier = Modifier
//                .fillMaxHeight()
//                .width(50.dp) // Wider region for gesture detection
//                .align(Alignment.CenterStart) // Ensure it aligns to the screen edge
//                .pointerInput(Unit) {
//                    detectHorizontalDragGestures(
//                        onHorizontalDrag = { _, dragAmount ->
//                            println("Drag detected: $dragAmount") // Debugging log
//                            scope.launch {
//                                panelOffsetX.animateTo(
//                                    (panelOffsetX.value + dragAmount).coerceIn(-panelWidthPx, 0f)
//                                )
//                            }
//                        },
//                        onDragEnd = {
//                            println("Drag ended with offset: ${panelOffsetX.value}") // Debugging log
//                            scope.launch {
//                                if (panelOffsetX.value > -panelWidthPx / 2) {
//                                    panelOffsetX.animateTo(0f, tween(300)) // Fully open
//                                    viewModel.openPanel()
////                                    onPanelStateChange(true)
//                                } else {
//                                    panelOffsetX.animateTo(-panelWidthPx, tween(300)) // Fully closed
//                                    viewModel.closePanel()
////                                    onPanelStateChange(false)
//                                }
//                            }
//                        }
//                    )
//                }
//                .background(Color.Transparent) // Transparent for interaction, visible for debugging
//        )
////        Box(
////            modifier = Modifier
////                .fillMaxHeight()
////                .width(with(LocalDensity.current) { panelWidth.value.toDp() })
////                .background(Color.Gray)
////                .pointerInput(Unit) {
////                    detectHorizontalDragGestures(
////                        onHorizontalDrag = { _, dragAmount ->
////                            scope.launch {
////                                // Update width dynamically during the drag
////                                val newWidth = (panelWidth.value + dragAmount).coerceIn(50f, 300f)
////                                updatePanelWidth(newWidth.toInt())
////                            }
////                        },
////                        onDragEnd = {
////                            scope.launch {
////                                if (panelWidth.value > 150f) {
//////                                    panelWidth.animateTo(300f) // Expand fully
////                                    viewModel.openPanel()
////                                } else {
//////                                    panelWidth.animateTo(50f) // Collapse
////                                    viewModel.closePanel()
////                                }
////                                updatePanelWidth(panelWidth.value.toInt())
////                            }
////                        }
////                    )
////                }
////        )
//
//        // Sliding panel with adjusted starting position
//        Box(
//            modifier = Modifier
//                .offset { IntOffset(panelOffsetX.value.roundToInt(), verticalOffset.roundToPx()) }
//                .height(panelHeight) // Reduced height
//                .width(panelWidth)
//                .background(Color.Gray)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(8.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                // Toggle Switch at the center
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 8.dp),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Switch(
//                        checked = viewModel.toggleState.value,
//                        onCheckedChange = { viewModel.updateToggleState(it) }
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Horizontal Sliders
//                Row(
//                    modifier = Modifier
//                        .fillMaxHeight(),
//                    horizontalArrangement = Arrangement.SpaceEvenly,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    // First Vertical Slider
//                    Box(
//                        modifier = Modifier
//                            .height(400.dp) // Increased height for larger slider
//                            .width(60.dp) // Increased width for better interaction
//                            .graphicsLayer { rotationZ = -90f }
//                    ) {
//                        Slider(
//                            value = viewModel.sliderValue1.value,
//                            onValueChange = { viewModel.updateSlider1(it) }
//                        )
//                    }
//
//                    // Second Vertical Slider
//                    Box(
//                        modifier = Modifier
//                            .height(400.dp) // Increased height for larger slider
//                            .width(60.dp) // Increased width for better interaction
//                            .graphicsLayer { rotationZ = -90f }
//                    ) {
//                        Slider(
//                            value = viewModel.sliderValue2.value,
//                            onValueChange = { viewModel.updateSlider2(it) }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun SlidingPanel(
    viewModel: SlidingPanelViewModel = viewModel(),
    updatePanelWidth: (Int) -> Unit // Callback to update width dynamically in the service
) {
//    val isPanelOpen by viewModel.isPanelVisible
//    val panelWidth = remember { Animatable(50f) }
    val panelWidth = 250.dp
    val panelHeight = 500.dp // Define the panel height
    val verticalOffset = 50.dp // Distance from the top of the screen
    val panelWidthPx = with(LocalDensity.current) { panelWidth.value }
//    val panelOffsetX = remember { Animatable(if (isPanelOpen) 0f else -panelWidthPx) }
    val panelOffsetX = remember{Animatable(-panelWidthPx)}
    var laysize = 50.dp
    val scope = rememberCoroutineScope()
    val closeThreshold = 250f

    val dragThreshold = 50f

    // Accumulated drag amount
    var accumulatedDrag by remember { mutableFloatStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Gesture detection for dragging
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            accumulatedDrag += dragAmount
                        },
                        onDragEnd = {
                            scope.launch {
                                when {
                                    accumulatedDrag > dragThreshold -> {
                                        updatePanelWidth(panelWidthPx.toInt())
                                        // Dragged right enough to open the panel
                                        panelOffsetX.animateTo(
                                            targetValue = 0f, // Fully open
                                            animationSpec = tween(
                                                durationMillis = 800,
                                                easing = FastOutSlowInEasing
                                            )
                                        )

                                        viewModel.openPanel()
                                    }
                                    accumulatedDrag < -dragThreshold -> {
                                        // Dragged left enough to close the panel
                                        panelOffsetX.animateTo(
                                            targetValue = -panelWidthPx, // Fully closed
                                            animationSpec = tween(
                                                durationMillis = 800,
                                                easing = FastOutSlowInEasing
                                            )
                                        )
                                        updatePanelWidth(50) // Minimum width for the edge
                                        viewModel.closePanel()
                                    }
                                    else -> {
                                        // Snap back to current state
                                        if (viewModel.isPanelVisible.value) {
                                            updatePanelWidth(panelWidthPx.toInt())
                                            panelOffsetX.animateTo(
                                                targetValue = 0f, // Fully open
                                                animationSpec = tween(
                                                    durationMillis = 800,
                                                    easing = FastOutSlowInEasing
                                                )
                                            ) // Snap to open

                                        } else {
                                            panelOffsetX.animateTo(-panelWidthPx) // Snap to closed
                                            updatePanelWidth(50)
                                        }
                                    }
                                }

                                accumulatedDrag = 0f // Reset drag after gesture
                            }
                        }
                    )
                }
        )

        // Sliding panel
        Box(

            modifier = Modifier
                .height(panelHeight) // Reduced height
                .offset(y = verticalOffset) // Start below the top
                .width(with(LocalDensity.current) { panelWidth.value.toDp() })
                .offset { IntOffset(panelOffsetX.value.roundToInt(), 0) }
                .background(Color.Red)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                // Row at the top
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color.Gray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Switch(
                        checked = viewModel.toggleState.value,
                        onCheckedChange = {
                            viewModel.updateToggleState(it)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Two columns evenly placed side by side
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color.LightGray),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                        val sliderValueLeft = remember { mutableStateOf(0f) }
                        Slider(
                            value = viewModel.sliderValue1.value,
                            onValueChange = { viewModel.updateSlider1(it) },
                            modifier = Modifier
                                .fillMaxHeight()
                                .rotate(270f)
                                .padding(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color.LightGray),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                        val sliderValueRight = remember { mutableStateOf(0f) }
                        Slider(
                            value = viewModel.sliderValue2.value,
                            onValueChange = { viewModel.updateSlider2(it) },
                            modifier = Modifier
                                .fillMaxHeight()
                                .rotate(270f)
                                .padding(16.dp)
                        )
                    }
                }
            }

        }
    }
}



