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
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class FloatingPanelService : Service(), ViewModelStoreOwner {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingPanelView: ComposeView
    private val lifecycleManager = FloatingPanelLifecycleManager()
    //    private val viewModelStore = CustomViewModelStoreOwner()
    private lateinit var viewModel: SlidingPanelViewModel // lateinit variable
    override val viewModelStore = ViewModelStore()
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val layoutParams = WindowManager.LayoutParams(
        50, // Small width for the edge panel
        600,

//        WindowManager.LayoutParams.MATCH_PARENT,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else
            WindowManager.LayoutParams.TYPE_PHONE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // Always interactive
        android.graphics.PixelFormat.TRANSLUCENT
    ).apply {
        gravity = Gravity.END or Gravity.TOP // Align to the left edge of the screen
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
//                SlidingPanel(
//                    viewModel = viewModel,
//                    onPanelStateChange = { isVisible ->
//                        updatePanelVisibility(isVisible)
//                    }
//                ) // Replace with your composable content
                SlidingPanel(viewModel) { isOpen ->
                    updatePanelLayout(isOpen)
                }

//                SlidingPanel(viewModel) { width ->
//                    updatePanelWidth(width)
//                }
            }
        }
        windowManager.addView(floatingPanelView, layoutParams)

    }




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



//    private fun updatePanelWidth(width: Int, height: Int) {
//        layoutParams.width = width
//        layoutParams.height = height
//        windowManager.updateViewLayout(floatingPanelView, layoutParams)
//    }


    private fun updatePanelLayout(isOpen: Boolean) {
        if (isOpen) {
            // Set to full screen when the panel is open
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        } else {
            // Set to edge size when the panel is closed
            layoutParams.width = 50
            layoutParams.height = 600
        }
        windowManager.updateViewLayout(floatingPanelView, layoutParams)
    }




    // Create permanent notification
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




