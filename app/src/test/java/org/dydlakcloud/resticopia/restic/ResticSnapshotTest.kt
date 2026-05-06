package org.dydlakcloud.resticopia.restic

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File
import java.time.ZonedDateTime

class ResticSnapshotTest {

    @MockK
    private lateinit var mokkFile: File

    lateinit var resticSnapshot: ResticSnapshot

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        resticSnapshot = ResticSnapshot(
            time = ZonedDateTime.parse("2024-01-01T00:00:00Z"),
            parent = null,
            tree = "tree-id",
            paths = listOf(mokkFile),
            hostname = "hostname",
            id = ResticSnapshotId("snapshot-id"),
            tags = listOf("tag1", "tag2")
        )
    }

    @Test
    fun verify_tags() {
        assertEquals(2, resticSnapshot.tags.size)
        assert(resticSnapshot.tags.contains("tag1"))
        assert(resticSnapshot.tags.contains("tag2"))
    }

    @Test
    fun verify_default_tags() {
        resticSnapshot = ResticSnapshot(
            time = ZonedDateTime.parse("2024-01-01T00:00:00Z"),
            parent = null,
            tree = "tree-id",
            paths = listOf(mokkFile),
            hostname = "hostname",
            id = ResticSnapshotId("snapshot-id")
        )

        assertEquals(0, resticSnapshot.tags.size)
    }
}