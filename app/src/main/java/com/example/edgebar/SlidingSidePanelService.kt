package com.example.edgebar

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class FloatingPanelService : Service(), ViewModelStoreOwner {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingPanelView: ComposeView
    private val lifecycleManager = FloatingPanelLifecycleManager()
//    private val viewModelStore = CustomViewModelStoreOwner()
    private lateinit var viewModel: SlidingPanelViewModel // lateinit variable
    override val viewModelStore = ViewModelStore()
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val layoutParams = WindowManager.LayoutParams(
        50, // Initial width set to 0
        WindowManager.LayoutParams.MATCH_PARENT,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else
            WindowManager.LayoutParams.TYPE_PHONE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        android.graphics.PixelFormat.TRANSLUCENT
    ).apply {
        gravity = Gravity.START or Gravity.TOP
    }




    override fun onCreate() {
        super.onCreate()
        lifecycleManager.setLifecycleState(Lifecycle.State.CREATED)
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        viewModel = ViewModelProvider(this)[SlidingPanelViewModel::class.java]






        // Set up the floating panel view
        floatingPanelView = ComposeView(this).apply {
            // Attach lifecycle and saved state registry owners
//            ViewTreeLifecycleOwner.set(this, lifecycleManager)
//            ViewTreeSavedStateRegistryOwner.set(this, lifecycleManager)
            setViewTreeLifecycleOwner(lifecycleOwner = lifecycleManager)
            setViewTreeSavedStateRegistryOwner(owner = lifecycleManager)
            setViewTreeViewModelStoreOwner(viewModelStoreOwner = this@FloatingPanelService)


            setContent {
                SlidingPanel(
                    viewModel = viewModel,
                    onPanelStateChange = { isVisible ->
                        updatePanelVisibility(isVisible)
                    }
                ) // Replace with your composable content
            }
        }
        windowManager.addView(floatingPanelView, layoutParams)

        observePanelVisibility()
    }

        // Configure window layout parameters
//        val layoutParams = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//            else
//                WindowManager.LayoutParams.TYPE_PHONE,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//            android.graphics.PixelFormat.TRANSLUCENT
//        )

//        val layoutParams = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//            else
//                WindowManager.LayoutParams.TYPE_PHONE,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // Allows touch events to pass through
//            android.graphics.PixelFormat.TRANSLUCENT
//        )
//        layoutParams.gravity = Gravity.TOP or Gravity.START
//        layoutParams.x = 0
//        layoutParams.y = 100
//
//        windowManager.addView(floatingPanelView, layoutParams)
//    }



    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(1, createNotification())
        lifecycleManager.setLifecycleState(Lifecycle.State.STARTED)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleManager.setLifecycleState(Lifecycle.State.DESTROYED)
        if (::floatingPanelView.isInitialized) {
            windowManager.removeView(floatingPanelView)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun observePanelVisibility() {
        serviceScope.launch {
            snapshotFlow { viewModel.isPanelVisible.value }
                .collect { isVisible ->
                    updatePanelVisibility(isVisible)
                }
        }
    }

    private fun updatePanelVisibility(isVisible: Boolean) {
        if (isVisible) {
            // Expand width and allow interaction
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        } else {
            // Collapse width and allow pass-through interaction
            layoutParams.width = 50
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        }
        windowManager.updateViewLayout(floatingPanelView, layoutParams)
    }





    private fun createNotification(): Notification {
        val notificationBuilder = Notification.Builder(this)
            .setContentTitle("Floating Panel Service")
            .setContentText("The floating panel is active.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId("floating_panel_channel")
        }

        return notificationBuilder.build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "floating_panel_channel",
                "Floating Panel Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}


