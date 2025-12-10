package dev.dongeeo.netuserspro

import dev.dongeeo.netuserspro.data.remote.ApiService
import dev.dongeeo.netuserspro.data.repository.UserRepository
import dev.dongeeo.netuserspro.domain.model.User
import dev.dongeeo.netuserspro.presentation.UserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val sampleUsers = listOf(
        User(id = 1, name = "Alice Johnson", email = "alice@test.com"),
        User(id = 2, name = "Bob Smith", email = "bob@test.com")
    )

    @Test
    fun `loadUsers updates state with data`() = runTest {
        val viewModel = UserViewModel(
            repository = UserRepository(
                api = object : ApiService {
                    override suspend fun getUsers(): List<User> = sampleUsers
                }
            )
        )

        viewModel.loadUsers()
        advanceUntilIdle()

        assertEquals(sampleUsers, viewModel.users.value)
        assertEquals(sampleUsers, viewModel.filteredUsers.value)
        assertNull(viewModel.error.value)
        assertEquals(false, viewModel.loading.value)
    }

    @Test
    fun `loadUsers sets error when repository fails`() = runTest {
        val viewModel = UserViewModel(
            repository = UserRepository(
                api = object : ApiService {
                    override suspend fun getUsers(): List<User> {
                        throw IllegalStateException("Boom")
                    }
                }
            )
        )

        viewModel.loadUsers()
        advanceUntilIdle()

        assertEquals("Boom", viewModel.error.value)
        assertTrue(viewModel.users.value.isEmpty())
        assertEquals(false, viewModel.loading.value)
    }

    @Test
    fun `onSearchQueryChange filters users by name and email`() = runTest {
        val viewModel = UserViewModel(
            repository = UserRepository(
                api = object : ApiService {
                    override suspend fun getUsers(): List<User> = sampleUsers
                }
            )
        )

        viewModel.loadUsers()
        advanceUntilIdle()

        viewModel.onSearchQueryChange("bob")
        assertEquals(listOf(sampleUsers[1]), viewModel.filteredUsers.value)

        viewModel.onSearchQueryChange("alice@test.com")
        assertEquals(listOf(sampleUsers[0]), viewModel.filteredUsers.value)

        viewModel.onSearchQueryChange("")
        assertEquals(sampleUsers, viewModel.filteredUsers.value)
    }
}

