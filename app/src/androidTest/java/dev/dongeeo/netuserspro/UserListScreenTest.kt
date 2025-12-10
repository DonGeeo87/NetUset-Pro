package dev.dongeeo.netuserspro

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.dongeeo.netuserspro.data.remote.ApiService
import dev.dongeeo.netuserspro.data.repository.UserRepository
import dev.dongeeo.netuserspro.domain.model.User
import dev.dongeeo.netuserspro.presentation.UserListScreen
import dev.dongeeo.netuserspro.presentation.UserViewModel
import dev.dongeeo.netuserspro.presentation.theme.NetUsersProTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserListScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val sampleUsers = listOf(
        User(id = 1, name = "Alice Johnson", email = "alice@test.com"),
        User(id = 2, name = "Bob Smith", email = "bob@test.com")
    )

    @Test
    fun showsUsersAndFiltersByQuery() {
        val viewModel = UserViewModel(
            repository = UserRepository(
                api = object : ApiService {
                    override suspend fun getUsers(): List<User> = sampleUsers
                }
            )
        )

        composeRule.setContent {
            NetUsersProTheme {
                UserListScreen(
                    viewModel = viewModel,
                    onUserClick = {}
                )
            }
        }

        composeRule.waitUntil(5_000) { viewModel.filteredUsers.value.isNotEmpty() }

        composeRule.onNodeWithText("Alice Johnson").assertIsDisplayed()
        composeRule.onNodeWithText("Bob Smith").assertIsDisplayed()

        composeRule.onNodeWithText("Buscar usuario").performTextInput("bob")
        composeRule.waitUntil(2_000) { viewModel.filteredUsers.value.size == 1 }

        composeRule.onNodeWithText("Bob Smith").assertIsDisplayed()
        composeRule.onAllNodesWithText("Alice Johnson").assertCountEquals(0)
    }

    @Test
    fun showsErrorStateWhenLoadFails() {
        val viewModel = UserViewModel(
            repository = UserRepository(
                api = object : ApiService {
                    override suspend fun getUsers(): List<User> {
                        throw IllegalStateException("Network error")
                    }
                }
            )
        )

        composeRule.setContent {
            NetUsersProTheme {
                UserListScreen(
                    viewModel = viewModel,
                    onUserClick = {}
                )
            }
        }

        composeRule.waitUntil(5_000) { viewModel.error.value != null }

        composeRule.onNodeWithText("Network error").assertIsDisplayed()
        composeRule.onNodeWithText("Reintentar").assertIsDisplayed()
    }
}

