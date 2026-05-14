package org.dydlakcloud.resticopia.util

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import kotlin.text.replace

object HostnameUtil {

    fun detectHostname(context: Context): String {
        // Some Devices do not have a BluetoothAdapter e.g. the Android Emulator. They should not pass the permission check
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return normalize((context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter.name)
        }

        val contentResolver = context.contentResolver
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            var deviceName = Settings.Global.getString(contentResolver, Settings.Global.DEVICE_NAME)
            if(deviceName == null) {
                deviceName = Settings.Secure.getString(contentResolver, "bluetooth_name")
            }
            if (deviceName != null) {
                return normalize(deviceName)
            }
        }
        return normalize("${Build.MANUFACTURER}-${Build.MODEL}")
    }

    private fun normalize(name: String): String {
        return name.replace(" ", "-").lowercase()
    }
}