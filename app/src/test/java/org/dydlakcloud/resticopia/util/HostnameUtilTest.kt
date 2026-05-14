package org.dydlakcloud.resticopia.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowBuild
import kotlin.intArrayOf

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.TIRAMISU], manifest = Config.NONE)
class HostnameUtilTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        ShadowBuild.setManufacturer("Samsung")
        ShadowBuild.setModel("Galaxy S26")
        ShadowBuild.setDevice("s26-ultra")
        context = mockk(relaxed = true)
    }

    @Test
    fun `test detectHostname returns default MANUFACTURER-MODEL`() {
        mockkStatic(ContextCompat::class) {
            every { ContextCompat.checkSelfPermission(any(), Manifest.permission.BLUETOOTH_CONNECT) } returns PackageManager.PERMISSION_DENIED

            val hostName = HostnameUtil.detectHostname(context)
            assertEquals("samsung-galaxy-s26", hostName)
        }
    }

    @Test
    fun `test detectHostname returns settings DEVICE_NAME`() {
        val context = RuntimeEnvironment.getApplication()

        Settings.Global.putString(context.contentResolver, Settings.Global.DEVICE_NAME, "My Pixel 10 Pro XL")
        mockkStatic(ContextCompat::class) {
            every { ContextCompat.checkSelfPermission(any(), Manifest.permission.BLUETOOTH_CONNECT) } returns PackageManager.PERMISSION_DENIED

            val hostName = HostnameUtil.detectHostname(context)
            assertEquals("my-pixel-10-pro-xl", hostName)
        }
    }

    @Test
    fun `test detectHostname returns settings bluetooth_name`() {
        val context = RuntimeEnvironment.getApplication()

        Settings.Secure.putString(context.contentResolver, "bluetooth_name", "My Pixel 11 Pro XL")
        mockkStatic(ContextCompat::class) {
            every { ContextCompat.checkSelfPermission(any(), Manifest.permission.BLUETOOTH_CONNECT) } returns PackageManager.PERMISSION_DENIED

            val hostName = HostnameUtil.detectHostname(context)
            assertEquals("my-pixel-11-pro-xl", hostName)
        }
    }
}