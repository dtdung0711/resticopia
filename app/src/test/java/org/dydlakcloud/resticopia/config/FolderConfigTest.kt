package org.dydlakcloud.resticopia.config

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

class FolderConfigTest {

    lateinit var folderConfig: FolderConfig

    @MockK
    private lateinit var mokkFile: File

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        folderConfig = FolderConfig(
            id = FolderConfigId.create(),
            repoId = RepoConfigId.create(),
            path = mokkFile,
            schedule = "Daily",
            tags = listOf("tag1", "tag2")
        )
    }

    @Test
    fun verify_tags() {
        assertEquals(2, folderConfig.tags.size)
        assert(folderConfig.tags.contains("tag1"))
        assert(folderConfig.tags.contains("tag2"))
    }
}