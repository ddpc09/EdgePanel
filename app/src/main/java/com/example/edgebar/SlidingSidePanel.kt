package com.example.edgebar

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun SlidingPanel(
    viewModel: SlidingPanelViewModel = viewModel(),
    updatePanelState: (Boolean) -> Unit // Callback to update panel layout state
) {
    val scope = rememberCoroutineScope()
    val panelHeight = 500.dp
    val panelWidthPx = with(LocalDensity.current) { 250.dp.toPx() }
//    val panelOffsetX = remember { Animatable(panelWidthPx) } // Start fully closed
    val screenWidthPx =
        with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() } // Screen width in pixels
    val panelOffsetX = remember { Animatable(screenWidthPx) }
    val dragThreshold = 50f
    var accumulatedDrag by remember { mutableFloatStateOf(0f) }
    val detectionBoxOffsetX = remember { Animatable(panelWidthPx) }



    Box(modifier = Modifier.fillMaxSize()) {
        // Sliding panel content
        Column(
            modifier = Modifier
                .offset { IntOffset(panelOffsetX.value.roundToInt(), 100) }
//                .offset { IntOffset(( panelWidthPx).roundToInt(), 0) }
//                .offset { IntOffset((panelOffsetX.value + panelWidthPx).roundToInt(), 0) }
                .width(250.dp)
                .height(panelHeight)
//                .background(Color.Red)

                .clip(
                    RoundedCornerShape(
                        topStart = 63.86111831665039.dp,
                        topEnd = 0.dp,
                        bottomStart = 63.86111831665039.dp,
                        bottomEnd = 0.dp
                    )
                )


                .background(Color(red = 0f, green = 0f, blue = 0f, alpha = 0.65f))
                .zIndex(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .zIndex(1f)
            ) {
                // Row at the top
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
//                        .background(Color.Gray)
                        .clip(
                            RoundedCornerShape(
                                topStart = 162.8787841796875.dp,
                                topEnd = 162.8787841796875.dp,
                                bottomStart = 162.8787841796875.dp,
                                bottomEnd = 162.8787841796875.dp
                            )
                        )
//                        .background(
//                            Color(
//                                red = 0.4745098f,
//                                green = 0.45490196f,
//                                blue = 0.49411765f,
//                                alpha = 1f
//                            )
//                        )
                        .background(Transparent)

                        .padding(
                            start = 6.515151500701904.dp,
                            top = 3.257575750350952.dp,
                            end = 6.515151500701904.dp,
                            bottom = 3.257575750350952.dp
                        )

                        .alpha(1f)
                        .zIndex(1f),
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

                Spacer(modifier = Modifier.height(60.dp))


                // Two columns evenly placed side by side
                Row(
                    modifier = Modifier
                        .height(260.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .zIndex(1f)
                            .fillMaxHeight()
//                            .clip(
//                                RoundedCornerShape(
//                                    topStart = 26.041950225830078.dp,
//                                    topEnd = 26.041950225830078.dp,
//                                    bottomStart = 26.041950225830078.dp,
//                                    bottomEnd = 26.041950225830078.dp
//                                )
//                            )
                            .clip(
                                RoundedCornerShape(
                                    topStart = 13.041950225830078.dp,
                                    topEnd = 13.041950225830078.dp,
                                    bottomStart = 13.041950225830078.dp,
                                    bottomEnd = 13.041950225830078.dp
                                )
                            )


//                            .background(Color(red = 0f, green = 0f, blue = 0f, alpha = 0.38f)),
                            .background(Transparent),
//                            .background(Color.LightGray),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        BrightnessSlider(
                            value = viewModel.sliderValue1.floatValue,
                            onValueChange = { viewModel.updateSlider1(it) },
                            modifier = Modifier
                                .weight(1f)
                                .zIndex(1f)
                                .height(220.dp)
                        )


                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .zIndex(1f)
                            .fillMaxHeight()
//                            .background(Color.LightGray),
                            .clip(
                                RoundedCornerShape(
                                    topStart = 13.041950225830078.dp,
                                    topEnd = 13.041950225830078.dp,
                                    bottomStart = 13.041950225830078.dp,
                                    bottomEnd = 13.041950225830078.dp
                                )
                            )
                            .background(Color.Transparent)

                            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

                            .alpha(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        WarmthSlider(
                            value = viewModel.sliderValue2.floatValue,
                            onValueChange = { viewModel.updateSlider2(it) },
                            modifier = Modifier
                                .weight(1f)
                                .zIndex(1f)
                                .height(220.dp)
                        )

                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

                // Row at the bottom with a ToggleButton
                Row(
                    modifier = Modifier

                        .zIndex(1f)
                        .height(50.dp)
                        .fillMaxWidth(),
//                        .background(Color.Gray),
//                        .clip(
//                            RoundedCornerShape(
//                                topStart = 68.90278625488281.dp,
//                                topEnd = 68.90278625488281.dp,
//                                bottomStart = 68.90278625488281.dp,
//                                bottomEnd = 68.90278625488281.dp
//                            )
//                        )
//                        .background(Color.Transparent)
//                        .border(
//                            1.680555820465088.dp,
//                            Color(
//                                red = 1f,
//                                green = 1f,
//                                blue = 0.8611109f,
//                                alpha = 0.2f
//                            ),
//                            RoundedCornerShape(
//                                topStart = 68.90278625488281.dp,
//                                topEnd = 68.90278625488281.dp,
//                                bottomStart = 68.90278625488281.dp,
//                                bottomEnd = 68.90278625488281.dp
//                            )
//                        )
//                        .padding(
//                            start = 20.166669845581055.dp,
//                            top = 20.166669845581055.dp,
//                            end = 20.166669845581055.dp,
//                            bottom = 20.166669845581055.dp
//                        )
//
//                        .alpha(1f),

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.updateToggleState2(true)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!viewModel.toggleState2.value) Color.Black else Color.Yellow,
                            contentColor = if (!viewModel.toggleState2.value) Color.White else Color.Black
                        ),
                        modifier = Modifier.border(
                            1.dp,
                            if (!viewModel.toggleState2.value) Color.White else Color.Black,
                            shape = RoundedCornerShape(50)
                        )
                    ) {
//                        Image(
//                            painter = painterResource(id = if(!viewModel.toggleState2.value) R.drawable.boost else R.drawable.booston),
//                            contentDescription = "Boost Image",
//                            contentScale = ContentScale.FillBounds,
//                            modifier = Modifier.width(30.dp).height(30.dp)
//                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Boost")
                    }


                }
            }
        }

            // Visual cue when the panel is closed
            if (!viewModel.isPanelVisible.value && !(accumulatedDrag < -dragThreshold)) {
                println("DetectionBoxvalue: ${detectionBoxOffsetX.value}")
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                detectionBoxOffsetX.value.roundToInt() - 285,
                                200
                            )
                        } // Stick to the left edge
//                    .offset((20).dp, (200).dp)
                        .width(10.dp) // Small width for the cue
                        .height(200.dp)
                        .zIndex(1f)
                        .background(Color.Gray.copy(alpha = 0.8f)) // Semi-transparent visual cue
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures(
                                onHorizontalDrag = { _, dragAmount ->
                                    accumulatedDrag += dragAmount
                                },
                                onDragEnd = {
                                    scope.launch {
                                        if (accumulatedDrag < -dragThreshold) {
                                            updatePanelState(true)
                                            viewModel.openPanel()
                                            // Dragged right enough to open the panel
                                            panelOffsetX.animateTo(
                                                targetValue = screenWidthPx - panelWidthPx, // Fully open
//                                            targetValue = screenWidthPx, // Fully open
                                                animationSpec = tween(
                                                    durationMillis = 800,
                                                    easing = FastOutSlowInEasing
                                                )
                                            )


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
                Column(
                    modifier = Modifier
//                    .offset(0.dp,0.dp)
                        .padding(end = 240.dp)
//                    .width((screenWidthPx-panelWidthPx).dp)
                        .fillMaxSize()
                        .zIndex(3f)
                        .background(Color.Transparent) // Semi-transparent overlay
//                    .clickable {
//                        // Close the panel when the overlay is clicked
//
//                        scope.launch {
//                            panelOffsetX.animateTo(screenWidthPx+panelWidthPx) // Close the panel
//                            updatePanelState(false)
//                            viewModel.closePanel()
//                        }
//                    }

                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            scope.launch {
                                panelOffsetX.animateTo(
//                                    targetValue =screenWidthPx+panelWidthPx,
                                    targetValue = screenWidthPx,
                                    animationSpec = tween(
                                        durationMillis = 800,
                                        easing = FastOutSlowInEasing
                                    )
                                ) // Close the panel
                                updatePanelState(false)
                                viewModel.closePanel()
                            }
                        }

//                horizontalAlignment = Alignment.Start


                ) {
                    println("Click registered")
                }
            }


            // Gesture detection for dragging
            Box(
                modifier = Modifier
                    .width(250.dp)
                    .height(500.dp)
                    .zIndex(2f)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                accumulatedDrag += dragAmount
                            },
                            onDragEnd = {
                                scope.launch {
                                    when {
                                        accumulatedDrag < -dragThreshold -> {
                                            updatePanelState(true)
                                            viewModel.openPanel()
                                            // Dragged right enough to open the panel
                                            panelOffsetX.animateTo(
//                                            (panelOffsetX.value + accumulatedDrag).coerceIn(-panelWidthPx, 0f),
                                                targetValue = screenWidthPx - panelWidthPx, // Fully open
                                                animationSpec = tween(
                                                    durationMillis = 800,
                                                    easing = FastOutSlowInEasing
                                                )
                                            )


                                        }

                                        accumulatedDrag > -dragThreshold -> {
                                            // Dragged left enough to close the panel
                                            panelOffsetX.animateTo(
//                                            targetValue = screenWidthPx+panelWidthPx, // Fully closed
                                                targetValue = screenWidthPx,
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
                                                viewModel.openPanel()
                                                panelOffsetX.animateTo(
                                                    targetValue = screenWidthPx - panelWidthPx, // Fully open
                                                    animationSpec = tween(
                                                        durationMillis = 800,
                                                        easing = FastOutSlowInEasing
                                                    )
                                                ) // Snap to open


                                            } else {
                                                panelOffsetX.animateTo(
                                                    targetValue = screenWidthPx,
                                                    animationSpec = tween(
                                                        durationMillis = 800,
                                                        easing = FastOutSlowInEasing
                                                    )
                                                ) // Snap to closed
                                                updatePanelState(false)
                                                viewModel.closePanel()
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


@Composable
fun BrightnessSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    sliderHeight: Dp = 230.dp,
    thumbSize: Dp = 60.dp,
    thumbWidth: Dp = 70.dp,
    thumbHeight: Dp = 30.dp,
    thumbColor: Color = Color.White,
    trackWidth: Dp = 60.dp,
    filledTrackColor: Color = Color.White,
    unfilledTrackColor: Color = Color.DarkGray,
    textColor: Color = Color.White
) {
    val sliderHeightPx = with(LocalDensity.current) { sliderHeight.toPx() }
    val thumbSizePx = with(LocalDensity.current) { thumbSize.toPx() }
    val scope = rememberCoroutineScope()

    // Track current drag offset
    var dragOffset by remember { mutableStateOf((1 - value) * sliderHeightPx) }

    // Update value based on drag offset
    LaunchedEffect(dragOffset) {
        val clampedValue = (1 - dragOffset / sliderHeightPx).coerceIn(0f, 1f)
        onValueChange(clampedValue)
    }

    Box(
        modifier = modifier
            .height(sliderHeight)
            .width(70.dp)
    ) {
        // Percentage Text


        // Unfilled Track
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(trackWidth)
                .align(Alignment.Center)
                .background(unfilledTrackColor, shape = RoundedCornerShape(20.dp)) // Adjusted corner radius
        ){
            Text(
                text = "${(value * 100).toInt()}%",
                color = textColor,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp)
            )
        }

        // Filled Track
        Box(
            modifier = Modifier
                .fillMaxHeight(fraction = value)
                .width(trackWidth)
                .align(Alignment.BottomCenter) // Align the filled track to the bottom
                .background(filledTrackColor, shape = RoundedCornerShape(20.dp)) // Adjusted corner radius
        )

        // Thumb
        Box(
            modifier = Modifier
                .offset { IntOffset(0, dragOffset.roundToInt()) }
                .size(width = thumbWidth, height = thumbHeight)
                .background(thumbColor, shape = RoundedCornerShape(10.dp)) // Ensure thumb matches
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        // Update dragOffset
                        dragOffset = (dragOffset + delta).coerceIn(0f, sliderHeightPx)

                        // Update value
                        val newValue = (1 - dragOffset / sliderHeightPx).coerceIn(0f, 1f)
                        onValueChange(newValue)
                    }
                )
        )
    }
}





@Composable
fun WarmthSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    sliderHeight: Dp = 230.dp,
    thumbSize: Dp = 60.dp,
    thumbWidth: Dp = 70.dp,
    thumbHeight: Dp = 30.dp,
    thumbColor: Color = Color.White,
    trackWidth: Dp = 60.dp,
    filledTrackColor: Color = Color(
        red = 1.00f,
        green = 1.00f,
        blue = 0.639f,
        alpha = 1f
    ),
    unfilledTrackColor: Color = Color.DarkGray,
    textColor: Color = Color.White
) {
    val sliderHeightPx = with(LocalDensity.current) { sliderHeight.toPx() }
    val thumbSizePx = with(LocalDensity.current) { thumbSize.toPx() }
    val scope = rememberCoroutineScope()

    // Track current drag offset
    var dragOffset by remember { mutableStateOf((1 - value) * sliderHeightPx) }

    // Update value based on drag offset
    LaunchedEffect(dragOffset) {
        val clampedValue = (1 - dragOffset / sliderHeightPx).coerceIn(0f, 1f)
        onValueChange(clampedValue)
    }

    Box(
        modifier = modifier
            .height(sliderHeight)
            .width(70.dp)
    ) {
        // Percentage Text


        // Unfilled Track
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(trackWidth)
                .align(Alignment.Center)
                .background(unfilledTrackColor, shape = RoundedCornerShape(20.dp)) // Adjusted corner radius
        )

        // Filled Track
        Box(
            modifier = Modifier
                .fillMaxHeight(fraction = value)
                .width(trackWidth)
                .align(Alignment.BottomCenter) // Align the filled track to the bottom
                .background(filledTrackColor, shape = RoundedCornerShape(20.dp)) // Adjusted corner radius
        )

        // Thumb
        Box(
            modifier = Modifier
                .offset { IntOffset(0, dragOffset.roundToInt()) }
                .size(width = thumbWidth, height = thumbHeight)
                .background(thumbColor, shape = RoundedCornerShape(10.dp)) // Ensure thumb matches
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        // Update dragOffset
                        dragOffset = (dragOffset + delta).coerceIn(0f, sliderHeightPx)

                        // Update value
                        val newValue = (1 - dragOffset / sliderHeightPx).coerceIn(0f, 1f)
                        onValueChange(newValue)
                    }
                )
        )
    }
}


