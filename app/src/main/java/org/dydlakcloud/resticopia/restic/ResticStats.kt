package org.dydlakcloud.resticopia.restic

import kotlinx.serialization.Serializable

@Serializable
data class ResticStats(
    val total_size: Long = 0,
    val total_file_count: Long? = null
)