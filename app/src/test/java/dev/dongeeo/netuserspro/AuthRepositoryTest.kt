package dev.dongeeo.netuserspro

import dev.dongeeo.netuserspro.data.remote.AuthRemoteDataSource
import dev.dongeeo.netuserspro.data.repository.AuthRepository
import dev.dongeeo.netuserspro.domain.model.AuthSession
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryTest {

    @Mock
    lateinit var remote: AuthRemoteDataSource

    private lateinit var repository: AuthRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = AuthRepository(remote)
    }

    @Test
    fun `login returns success when remote succeeds`() = runTest {
        val session = AuthSession(email = "user@test.com", token = "token123")
        whenever(remote.login("user@test.com", "password123")).thenReturn(session)

        val result = repository.login("user@test.com", "password123")

        assertTrue(result.isSuccess)
        assertEquals(session, result.getOrNull())
        verify(remote).login("user@test.com", "password123")
    }

    @Test
    fun `login returns failure when remote throws`() = runTest {
        whenever(remote.login("user@test.com", "password123")).thenThrow(IllegalStateException("Network error"))

        val result = repository.login("user@test.com", "password123")

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
        verify(remote).login("user@test.com", "password123")
    }
}


