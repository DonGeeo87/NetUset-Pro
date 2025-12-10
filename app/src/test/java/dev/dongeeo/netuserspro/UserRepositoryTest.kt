package dev.dongeeo.netuserspro

import dev.dongeeo.netuserspro.data.remote.ApiService
import dev.dongeeo.netuserspro.data.repository.UserRepository
import dev.dongeeo.netuserspro.domain.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {

    private val sampleUsers = listOf(
        User(id = 1, name = "Alice Johnson", email = "alice@test.com"),
        User(id = 2, name = "Bob Smith", email = "bob@test.com")
    )

    @Test
    fun `fetchUsers returns success when api succeeds`() = runTest {
        val repository = UserRepository(
            api = object : ApiService {
                override suspend fun getUsers(): List<User> = sampleUsers
            }
        )

        val result = repository.fetchUsers()

        assertTrue(result.isSuccess)
        assertEquals(sampleUsers, result.getOrNull())
    }

    @Test
    fun `fetchUsers returns failure when api throws`() = runTest {
        val repository = UserRepository(
            api = object : ApiService {
                override suspend fun getUsers(): List<User> {
                    throw IllegalStateException("Network down")
                }
            }
        )

        val result = repository.fetchUsers()

        assertTrue(result.isFailure)
        assertEquals("Network down", result.exceptionOrNull()?.message)
    }
}

