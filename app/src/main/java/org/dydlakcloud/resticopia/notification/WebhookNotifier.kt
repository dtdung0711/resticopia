package org.dydlakcloud.resticopia.notification

import java.io.BufferedOutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.CompletableFuture

object WebhookNotifier {

    fun sendWebhook(
        webhookUrl: String?,
        onSuccess: Boolean,
        onFailure: Boolean,
        isSuccess: Boolean,
        hostname: String?,
        folderPath: String,
        errorMessage: String? = null,
        headers: Map<String, String>? = null
    ): CompletableFuture<Unit> {
        return CompletableFuture.supplyAsync {
            if (webhookUrl.isNullOrBlank()) {
                return@supplyAsync
            }

            val shouldSend = if (isSuccess) onSuccess else onFailure
            if (!shouldSend) {
                return@supplyAsync
            }

            try {
                val url = URL(webhookUrl.trim())
                val connection = url.openConnection() as HttpURLConnection

                try {
                    connection.requestMethod = "POST"
                    connection.doOutput = true
                    connection.connectTimeout = 10000
                    connection.readTimeout = 10000
                    connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8")

                    headers?.forEach { (key, value) ->
                        connection.setRequestProperty(key, value)
                    }

                    val device = hostname ?: "Unknown Device"
                    val message = if (isSuccess) {
                        "Backup successful: $folderPath ($device)"
                    } else {
                        "Backup failed: $folderPath ($device)\nError: $errorMessage"
                    }

                    BufferedOutputStream(connection.outputStream).use { outputStream ->
                        OutputStreamWriter(outputStream, StandardCharsets.UTF_8).use { writer ->
                            writer.write(message)
                            writer.flush()
                        }
                    }

                    val responseCode = connection.responseCode
                    if (responseCode !in 200..299) {
                        println("WebhookNotifier: Webhook returned HTTP $responseCode")
                    } else {
                        println("WebhookNotifier: Webhook sent successfully")
                    }
                } finally {
                    connection.disconnect()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("WebhookNotifier: Error sending webhook: ${e.message}")
            }
        }
    }
}
