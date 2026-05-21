package org.dydlakcloud.resticopia.restic

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.io.File
import java.time.Duration
import java.util.concurrent.CompletableFuture


class ResticRepoTest {

    @MockK
    lateinit var restic: Restic
    @MockK
    lateinit var file0: File
    @MockK
    lateinit var file1: File
    @MockK
    lateinit var file2: File

    lateinit var repo: ResticRepo

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { restic.hostname } returns "pixel-7-pro"
        every { file0.absolutePath } returns "/path/to/file0"
        every { file1.absolutePath } returns "/path/to/file1"
        every { file2.absolutePath } returns "/path/to/file2"
        repo = object : ResticRepo(restic, "password") {
            override fun repository(): String {
                return "repository"
            }
        }
    }

    @Test
    fun `validate object create`() {
        assertNotNull(repo)
    }

    @Test
    fun `validate forget`() {
        val sampleResticJson = """
        [
          {
            "group": {
              "host": "pixel-7-pro",
              "paths": "/home/user/projects"
            },
            "keep": [
              {
                "id": "3a1b2c3d",
                "time": "2026-05-21T12:00:00.000Z",
                "tree": "a6fd83c2e1b94d76a213e45fbc89d0123e456789abcdef0123456789abcdef12",
                "host": "pixel-7-pro",
                "hostname": "pixel-7-pro",
                "paths": ["/home/user/projects"],
                "reasons": ["keep-daily"]
              }
            ],
            "remove": [
              {
                "id": "9f8e7d6c",
                "time": "2026-05-14T12:00:00.000Z",
                "tree": "b7ec83c2e1b94d76a213e45fbc89d0123e456789abcdef0123456789abcdef34",
                "host": "pixel-7-pro",
                "hostname": "pixel-7-pro",
                "paths": ["/home/user/projects"]
              }
            ]
          }
        ]
        """.trimIndent()
        every {
            restic.restic(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns CompletableFuture.completedFuture(Pair(listOf(sampleResticJson), emptyList()))
        val paths = listOf(file0, file1)
        var forgetResult: List<ResticSnapshot>? = null
        var forgetException: Throwable? = null
        repo.forget(paths, 1, Duration.ZERO, true).handle { result, exception ->
            forgetResult = result
            forgetException = exception
        }.join()
        assertNotNull(forgetResult)
        assertEquals(1, forgetResult?.size)
        assertNotNull(forgetResult?.get(0))
        val resticSnapshot = forgetResult?.get(0)!!
        assertEquals("b7ec83c2e1b94d76a213e45fbc89d0123e456789abcdef0123456789abcdef34", resticSnapshot.tree)
        assertEquals("9f8e7d6c", resticSnapshot.id.id)
        assertEquals("pixel-7-pro", resticSnapshot.hostname)
        assertEquals(0, resticSnapshot.tags.size)
        assertEquals(1, resticSnapshot.paths.size)
        assertEquals("/home/user/projects", resticSnapshot.paths[0].absolutePath)

        assertNull(forgetException)
    }
}