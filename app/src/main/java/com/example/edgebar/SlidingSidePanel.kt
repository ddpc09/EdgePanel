package com.example.edgebar

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
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
    val panelHeight = 500.dp//panel height
    val panelWidthPx = with(LocalDensity.current) { 250.dp.toPx() }//panel width
//    val panelOffsetX = remember { Animatable(panelWidthPx) } // Start fully closed
    var screenWidthPx = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() } // Screen width in pixels
    var panelOffsetX = remember { Animatable(screenWidthPx) }
    val dragThreshold = 50f
    var accumulatedDrag by remember { mutableFloatStateOf(0f) }
    val detectionBoxOffsetX = remember { Animatable(panelWidthPx) }
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation


    Box(modifier = Modifier.fillMaxSize()) {
        // Sliding panel content
        Column(
            modifier = Modifier
                .offset { IntOffset(panelOffsetX.value.roundToInt(), 100) }
//                .offset { IntOffset(( panelWidthPx).roundToInt(), 0) }
//                .offset { IntOffset((panelOffsetX.value + panelWidthPx).roundToInt(), 0) }
                .width(250.dp)
                .height(panelHeight)
//                .background(Color.Red) //for testing

                .clip(
                    RoundedCornerShape(
                        topStart = 63.86111831665039.dp,
                        topEnd = 0.dp,
                        bottomStart = 63.86111831665039.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(Color(red = 0f, green = 0f, blue = 0f, alpha = 0.65f))

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                // Row at the top
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .scale(1.3f) // Increase the scale of the Switch
                    ) {
                        Switch(
                            checked = viewModel.toggleState1.value,
                            onCheckedChange = {
                                viewModel.updateToggleState1(it)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                  Column(modifier = Modifier
                      .weight(1f)
                      .fillMaxHeight()) {
                      Image(
                          painter = painterResource(id = R.drawable.sun), // Replace with your image name
                          contentDescription = "My Image", // For accessibility
                          modifier = Modifier.size(100.dp), // Adjust the size as needed
                          contentScale = ContentScale.Fit // Adjust how the image should scale
                      )

                  }
                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()) { Image(
                        painter = painterResource(id = R.drawable.thermometer), // Replace with your image name
                        contentDescription = "My Image", // For accessibility
                        modifier = Modifier.size(100.dp), // Adjust the size as needed
                        contentScale = ContentScale.Fit // Adjust how the image should scale
                    ) }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Two columns evenly placed side by side
                Row(
                    modifier = Modifier
                        .height(220.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .zIndex(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        BrightnessSlider(
                            value = viewModel.sliderValue1.floatValue,
                            onValueChange = { viewModel.updateSlider1(it) },
                            modifier = Modifier
                                .weight(1f)
                                .height(180.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        WarmthSlider(
                            value = viewModel.sliderValue2.floatValue,
                            onValueChange = { viewModel.updateSlider2(it) },
                            modifier = Modifier
                                .weight(1f)
                                .height(180.dp)
                        )

                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

                // Row at the bottom with a ToggleButton
                Row(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
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
                            2.dp,
                            if (!viewModel.toggleState2.value) Color.White else Color.Black,
                            shape = RoundedCornerShape(50)
                        )
                    ) {
                        Image(
                            painter = painterResource(id = if(!viewModel.toggleState2.value) R.drawable.boost else R.drawable.booston),
                            contentDescription = "Boost Image",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.width(30.dp).height(30.dp)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Boost")
                    }


                }
            }
        }

            // Visual cue bar when the panel is closed
            if (!viewModel.isPanelVisible.value && !(accumulatedDrag < -dragThreshold)) {
                println("DetectionBoxvalue: ${detectionBoxOffsetX.value}")
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
//                                detectionBoxOffsetX.value.roundToInt() ,
                                40,
                                200
                            )
                        } // Stick to the left edge
//                    .offset((20).dp, (200).dp)
                        .width(10.dp) // Small width for the cue
                        .height(200.dp)
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
                        .background(Color.Transparent) // Semi-transparent overlay
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
//      Calculate screen width again in case the device rotation change
        LaunchedEffect(orientation) {
            if (viewModel.isPanelVisible.value) {
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
                    if (!viewModel.isPanelVisible.value) {
                        panelOffsetX.snapTo(screenWidthPx)
                    }
                }
            }
        }


            // Gesture detection for dragging
            Box(
                modifier = Modifier
                    .width(250.dp)
                    .height(500.dp)
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

// Custom Brightness Slider
@Composable
fun BrightnessSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    sliderHeight: Dp = 200.dp,
    gradientHeight: Dp = 200.dp,
    thumbSize: Dp = 60.dp,
    thumbWidth: Dp = 70.dp,
    thumbHeight: Dp = 30.dp,
    thumbColor: Color = Color.LightGray,
    trackWidth: Dp = 60.dp,
    filledTrackColor: Color = Color.White,
    unfilledTrackColor: Color = Color.DarkGray,
    cornerRadius: Dp = 4.dp,
    shadowElevation: Dp = 12.dp, // Elevation for the shadow
    shadowColor: Color = Color.Black.copy(alpha = 1f),
    gradientSliderColors: List<Color> = listOf(Color.White,Color.Gray),
    gradientThumbColors: List<Color> = listOf(Color.White,Color.Gray),
    textColor: Color = Color.White,

) {
    val sliderHeightPx = with(LocalDensity.current) { sliderHeight.toPx() }
    val gradientHeightPx= with(LocalDensity.current) { sliderHeight.toPx() }
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
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    // Calculate the clicked value based on the y-coordinate
                    val clickedValue = (1 - offset.y / sliderHeightPx).coerceIn(0f, 1f)
                    dragOffset = (1 - clickedValue) * sliderHeightPx
                    onValueChange(clickedValue)
                }
            }
    ) {



        // Unfilled Track
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(trackWidth)
                .align(Alignment.Center)
                .background(unfilledTrackColor,
                    shape = RoundedCornerShape(
                        15.dp
                    )) // Adjusted corner radius
        ){
            Text(
                text = "${(value * 100).toInt()}%",
                color = textColor,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp)
            )
        }

        // Filled Track
        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .offset(y = (thumbHeight / 10))
                .width(trackWidth)
                .align(Alignment.Center)
        ) {

            // Calculate the height of the filled portion
            val filledHeight = size.height * value

            // Draw the gradient starting from the bottom
            val gradient = Brush.verticalGradient(
                colors = gradientSliderColors,
                startY = 0f,
                endY = size.height
            )
            drawRoundRect(
                brush = gradient,
                topLeft = Offset(x = 0f, y = size.height - filledHeight), // Start from bottom
                size = Size(width = size.width, height = filledHeight),
                cornerRadius = CornerRadius(15f,15f)
            )
        }

        // Thumb
        Box(
            modifier = Modifier
                .offset { IntOffset(0, dragOffset.roundToInt()) }
                .size(width = thumbWidth, height = thumbHeight)
                .shadow(
                    elevation = shadowElevation,
                    shape = RoundedCornerShape(cornerRadius),
                    ambientColor = shadowColor,
                    spotColor = shadowColor
                )
                .background(brush = Brush.verticalGradient(
                    colors = gradientThumbColors,
                    startY = 0f,
                    endY = 30f
                ), shape = RoundedCornerShape(20.dp)) // Ensure thumb matches
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




// Custom warmth slider
@Composable
fun WarmthSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    sliderHeight: Dp = 200.dp,
    thumbSize: Dp = 60.dp,
    thumbWidth: Dp = 70.dp,
    thumbHeight: Dp = 30.dp,
    thumbColor: Color = Color.White,
    trackWidth: Dp = 60.dp,
    cornerRadiusPx: Dp = 10.dp,
    shadowElevation: Dp = 12.dp, // Higher elevation for a stronger shadow
    shadowColor: Color = Color.Black.copy(alpha = 1f), // Slightly darker shadow
    cornerRadius: Dp = 16.dp,
    gradientSliderColors: List<Color> = listOf(Color(
        red = 1.00f,
        green = 1.00f,
        blue = 0.639f,
        alpha = 1f
    ),Color.White),
    gradientThumbColors: List<Color> = listOf(Color(
        red = 1.00f,
        green = 1.00f,
        blue = 0.639f,
        alpha = 1f
    ),Color.White),
    unfilledTrackColor: Color = Color.DarkGray,
    textColor: Color = Color.White
) {
    val sliderHeightPx = with(LocalDensity.current) { sliderHeight.toPx() }
    val thumbSizePx = with(LocalDensity.current) { thumbSize.toPx() }
    val shadowElevationPx = with(LocalDensity.current) { shadowElevation.toPx() }
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
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    // Calculate the clicked value based on the y-coordinate
                    val clickedValue = (1 - offset.y / sliderHeightPx).coerceIn(0f, 1f)
                    dragOffset = (1 - clickedValue) * sliderHeightPx
                    onValueChange(clickedValue)
                }
            }
    ) {
        // Percentage Text


        // Unfilled Track
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(trackWidth)
                .align(Alignment.Center)
                .background(unfilledTrackColor, shape = RoundedCornerShape(15.dp)) // Adjusted corner radius
        )
        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .offset(y = (thumbHeight / 10))
                .width(trackWidth)
                .align(Alignment.Center)
                .background(color = Color.Transparent,
                    shape =
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    ))
        ) {

            // Calculate the height of the filled portion
            val filledHeight = size.height * value

            // Draw the gradient starting from the bottom
            val gradient = Brush.verticalGradient(
                colors = gradientSliderColors,
                startY = 0f,
                endY = size.height
            )
            drawRoundRect(
                brush = gradient,
                topLeft = Offset(x = 0f, y = size.height - filledHeight), // Start from bottom
                size = Size(width = size.width, height = filledHeight),
                cornerRadius = CornerRadius(15f, 15f)
            )
        }
        // Thumb
        Box(
            modifier = Modifier
                .offset { IntOffset(0, dragOffset.roundToInt()) }
                .size(width = thumbWidth, height = thumbHeight)
                .shadow(
                    elevation = shadowElevation,
                    shape = RoundedCornerShape(cornerRadius),
                    ambientColor = shadowColor,
                    spotColor = shadowColor
                )

                .background(brush = Brush.verticalGradient(
                    colors = gradientThumbColors,
                    startY = 0f,
                    endY = 60f
                ),

                    shape = RoundedCornerShape(10.dp)) // Ensure thumb matches
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








