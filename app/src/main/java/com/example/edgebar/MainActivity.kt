package com.example.edgebar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.core.app.JobIntentService


//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//                SlidingPanel()
//
//        }
//    }
//}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        if (canDrawOverlays()) {
            startFloatingPanelService()
        } else {
            requestOverlayPermission()
        }

        setContent {
            Text("Requesting permission to display the floating panel...")


        }
    }

    private fun canDrawOverlays(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(this)
        } else {
            true
        }
    }

    private fun requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, 0)
        }
    }

    private fun startFloatingPanelService() {
        val serviceIntent = Intent(this, FloatingPanelService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }

}
// method to detect boot
class BootBroadcastReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent){
        if(intent.action == Intent.ACTION_BOOT_COMPLETED){
            val serviceIntent = Intent(context, FloatingPanelService::class.java)
            context.startService(serviceIntent)

            Toast.makeText(context, "Service Started", Toast.LENGTH_SHORT).show()
        }
    }
}
// launch the service when system boots
class FloatingPanelJobIntentService : JobIntentService(){
    override fun onHandleWork(intent: Intent) {
        val serviceIntent = Intent(
            this,
            FloatingPanelService::class.java
        )
        startService(serviceIntent)
    }
    companion object{
        const val JOB_ID: Int = 1000

        fun enqueueWork(context: Context?, work: Intent?){
            enqueueWork(
                context!!,
                FloatingPanelJobIntentService::class.java, JOB_ID,
                work!!
            )
        }
    }
}