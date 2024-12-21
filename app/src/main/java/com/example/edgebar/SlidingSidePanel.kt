package com.example.edgebar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
//    viewModel: SlidingPanelViewModel = viewModel(),
//    updatePanelState: (Boolean) -> Unit // Callback to update panel layout state
//) {
////    val isPanelOpen by viewModel.isPanelVisible
////    val panelWidth = remember { Animatable(50f) }
//    val panelWidth = 250.dp
//    val panelHeight = 500.dp // Define the panel height
//    val verticalOffset = 50.dp // Distance from the top of the screen
//    val panelWidthPx = with(LocalDensity.current) { panelWidth.value }
////    val panelOffsetX = remember { Animatable(if (isPanelOpen) 0f else -panelWidthPx) }
//    val panelOffsetX = remember{Animatable(-panelWidthPx)}
//    var laysize = 50.dp
//    val scope = rememberCoroutineScope()
//    val closeThreshold = 250f
//
//    val dragThreshold = 50f
//
//    // Accumulated drag amount
//    var accumulatedDrag by remember { mutableFloatStateOf(0f) }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//
//
//        if (viewModel.isPanelVisible.value) {
//            Column (
//                modifier = Modifier
//                    .fillMaxSize()
//                    .zIndex(1f)
//                    .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent overlay
//                    .clickable {
//                        // Close the panel when the overlay is clicked
//
//                        scope.launch {
//                            panelOffsetX.animateTo(-panelWidthPx) // Close the panel
//                            updatePanelState(false)
//                            viewModel.closePanel()
//                        }
//                    }
//                    .padding(start = 250.dp),
//                horizontalAlignment = Alignment.End
//
//
//            ){
//                println("Click registered")
//            }
//        }
//
//
//
//
//        // Gesture detection for dragging
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .pointerInput(Unit) {
//                    detectHorizontalDragGestures(
//                        onHorizontalDrag = { _, dragAmount ->
//                            accumulatedDrag += dragAmount
//                        },
//                        onDragEnd = {
//                            scope.launch {
//                                when {
//                                    accumulatedDrag > dragThreshold -> {
//                                        updatePanelState(true)
//                                        // Dragged right enough to open the panel
//                                        panelOffsetX.animateTo(
//                                            targetValue = 0f, // Fully open
//                                            animationSpec = tween(
//                                                durationMillis = 800,
//                                                easing = FastOutSlowInEasing
//                                            )
//                                        )
//
//                                        viewModel.openPanel()
//                                    }
//                                    accumulatedDrag < -dragThreshold -> {
//                                        // Dragged left enough to close the panel
//                                        panelOffsetX.animateTo(
//                                            targetValue = -panelWidthPx, // Fully closed
//                                            animationSpec = tween(
//                                                durationMillis = 800,
//                                                easing = FastOutSlowInEasing
//                                            )
//                                        )
//                                        updatePanelState(false) // Minimum width for the edge
//                                        viewModel.closePanel()
//                                    }
//                                    else -> {
//                                        // Snap back to current state
//                                        if (viewModel.isPanelVisible.value) {
//                                            updatePanelState(true)
//                                            panelOffsetX.animateTo(
//                                                targetValue = 0f, // Fully open
//                                                animationSpec = tween(
//                                                    durationMillis = 800,
//                                                    easing = FastOutSlowInEasing
//                                                )
//                                            ) // Snap to open
//
//                                        } else {
//                                            panelOffsetX.animateTo(-panelWidthPx) // Snap to closed
//                                            updatePanelState(false)
//                                        }
//                                    }
//                                }
//
//                                accumulatedDrag = 0f // Reset drag after gesture
//                            }
//                        }
//                    )
//                }
//        )
//
//        // Sliding panel
//        Box(
//
//            modifier = Modifier
//                .height(panelHeight) // Reduced height
//                .offset(y = verticalOffset) // Start below the top
//                .width(with(LocalDensity.current) { panelWidth.value.toDp() })
//                .offset { IntOffset(panelOffsetX.value.roundToInt(), 0) }
//                .background(Color.Red)
//                .zIndex(2f)
//        ) {
//            Column(modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//                .zIndex(2f)
//            ) {
//                // Row at the top
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(60.dp)
//                        .background(Color.Gray)
//                        .zIndex(2f),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Switch(
//                        checked = viewModel.toggleState.value,
//                        onCheckedChange = {
//                            viewModel.updateToggleState(it)
//                        }
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Two columns evenly placed side by side
//                Row(
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .weight(1f)
//                            .fillMaxHeight()
//                            .background(Color.LightGray),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
////                        val sliderValueLeft = remember { mutableStateOf(0f) }
//                        Slider(
//                            value = viewModel.sliderValue1.value,
//                            onValueChange = { viewModel.updateSlider1(it) },
//                            modifier = Modifier
//                                .fillMaxHeight()
//                                .rotate(270f)
//                                .padding(16.dp)
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.width(16.dp))
//
//                    Column(
//                        modifier = Modifier
//                            .weight(1f)
//                            .fillMaxHeight()
//                            .background(Color.LightGray),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
////                        val sliderValueRight = remember { mutableStateOf(0f) }
//                        Slider(
//                            value = viewModel.sliderValue2.value,
//                            onValueChange = { viewModel.updateSlider2(it) },
//                            modifier = Modifier
//                                .fillMaxHeight()
//                                .rotate(270f)
//                                .padding(16.dp)
//                        )
//                    }
//                }
//            }
//
//        }
//    }
//}


@Composable
fun SlidingPanel(
    viewModel: SlidingPanelViewModel = viewModel(),
    updatePanelState: (Boolean) -> Unit // Callback to update panel layout state
) {
    val scope = rememberCoroutineScope()
    val panelHeight = 600.dp
    val panelWidthPx = with(LocalDensity.current) { 250.dp.toPx() }
    val panelOffsetX = remember { Animatable(-panelWidthPx) } // Start fully closed
    val dragThreshold = 50f
    var accumulatedDrag by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Sliding panel content
        Box(
            modifier = Modifier
                .offset { IntOffset(panelOffsetX.value.roundToInt(), 0) }
                .width(250.dp)
                .height(panelHeight)
                .background(Color.Red)
                .zIndex(2f)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .zIndex(2f)
            ) {
                // Row at the top
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(Color.Gray)
                        .zIndex(2f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Switch(
                        checked = viewModel.toggleState1.value,
                        onCheckedChange = {
                            viewModel.updateToggleState1(it)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Two columns evenly placed side by side
                Row(
                    modifier = Modifier
                        .height(450.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .zIndex(2f)
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
                            .zIndex(2f)
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
                Spacer(modifier = Modifier.height(16.dp))

                // Row at the bottom with a ToggleButton
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(2f)
                        .height(60.dp)
                        .background(Color.Gray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {

                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Button")
                    }
                }
            }
        }

        // Visual cue when the panel is closed
        if (!viewModel.isPanelVisible.value) {
            Box(
                modifier = Modifier
                    .offset(x = 0.dp, y = 200.dp) // Stick to the left edge
                    .width(20.dp) // Small width for the cue
                    .height(200.dp)
                    .zIndex(2f)
                    .background(Color.Gray.copy(alpha = 0.5f)) // Semi-transparent visual cue
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                accumulatedDrag += dragAmount
                            },
                            onDragEnd = {
                                scope.launch {
                                    if (accumulatedDrag > dragThreshold) {
                                        updatePanelState(true)
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
                                    accumulatedDrag = 0f
                                }
                            }
                        )
                    }
            )
        }

        // Overlay to detect clicks outside the panel
        if (viewModel.isPanelVisible.value) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f)
                    .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent overlay
                    .clickable {
                        // Close the panel when the overlay is clicked

                        scope.launch {
                            panelOffsetX.animateTo(-panelWidthPx) // Close the panel
                            updatePanelState(false)
                            viewModel.closePanel()
                        }
                    }
                    .padding(start = 250.dp)


            ){
                println("Click registered")
            }
        }



        // Gesture detection for dragging
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            accumulatedDrag += dragAmount
                        },
                        onDragEnd = {
                            scope.launch {
                                when {
                                    accumulatedDrag > dragThreshold -> {
                                        updatePanelState(true)
                                        // Dragged right enough to open the panel
                                        panelOffsetX.animateTo(
//                                            (panelOffsetX.value + accumulatedDrag).coerceIn(-panelWidthPx, 0f),
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
                                        updatePanelState(false) // Minimum width for the edge
                                        viewModel.closePanel()
                                    }
                                    else -> {
                                        // Snap back to current state
                                        if (viewModel.isPanelVisible.value) {
                                            updatePanelState(true)
                                            panelOffsetX.animateTo(
                                                targetValue = 0f, // Fully open
                                                animationSpec = tween(
                                                    durationMillis = 800,
                                                    easing = FastOutSlowInEasing
                                                )
                                            ) // Snap to open

                                        } else {
                                            panelOffsetX.animateTo(-panelWidthPx) // Snap to closed
                                            updatePanelState(false)
                                        }
                                    }
                                }

                                accumulatedDrag = 0f // Reset drag after gesture
                            }
                        }
                    )
                }
        )
    }
}



