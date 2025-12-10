package dev.dongeeo.netuserspro

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.dongeeo.netuserspro.data.remote.AuthRemoteDataSource
import dev.dongeeo.netuserspro.data.repository.AuthRepository
import dev.dongeeo.netuserspro.domain.model.AuthSession
import dev.dongeeo.netuserspro.domain.validation.CredentialsValidator
import dev.dongeeo.netuserspro.presentation.LoginScreen
import dev.dongeeo.netuserspro.presentation.LoginViewModel
import dev.dongeeo.netuserspro.presentation.theme.NetUsersProTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun successfulLoginShowsSuccessMessage() {
        val viewModel = LoginViewModel(
            repository = AuthRepository(
                remote = object : AuthRemoteDataSource {
                    override suspend fun login(email: String, password: String): AuthSession {
                        return AuthSession(email = email, token = "token-123")
                    }
                }
            ),
            validator = CredentialsValidator()
        )

        composeRule.setContent {
            NetUsersProTheme {
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {}
                )
            }
        }

        composeRule.onNodeWithText("Correo electrónico").performTextInput("user@test.com")
        composeRule.onNodeWithText("Contraseña").performTextInput("password123")
        composeRule.onNodeWithText("Iniciar sesión").performClick()

        composeRule.waitUntil(3_000) { viewModel.state.value.isSuccess }
        composeRule.onNodeWithText("Sesión iniciada").assertIsDisplayed()
    }

    @Test
    fun invalidCredentialsShowErrorMessage() {
        val viewModel = LoginViewModel(
            repository = AuthRepository(
                remote = object : AuthRemoteDataSource {
                    override suspend fun login(email: String, password: String): AuthSession {
                        throw IllegalArgumentException("Credenciales inválidas")
                    }
                }
            ),
            validator = CredentialsValidator()
        )

        composeRule.setContent {
            NetUsersProTheme {
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {}
                )
            }
        }

        composeRule.onNodeWithText("Correo electrónico").performTextInput("user@test.com")
        composeRule.onNodeWithText("Contraseña").performTextInput("password123")
        composeRule.onNodeWithText("Iniciar sesión").performClick()

        composeRule.waitUntil(3_000) { viewModel.state.value.error != null }
        composeRule.onNodeWithText("Credenciales inválidas").assertIsDisplayed()
    }

    @Test
    fun validationBlocksEmptyEmail() {
        val viewModel = LoginViewModel(
            repository = AuthRepository(
                remote = object : AuthRemoteDataSource {
                    override suspend fun login(email: String, password: String): AuthSession {
                        return AuthSession(email = email, token = "token-123")
                    }
                }
            ),
            validator = CredentialsValidator()
        )

        composeRule.setContent {
            NetUsersProTheme {
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {}
                )
            }
        }

        composeRule.onNodeWithText("Contraseña").performTextInput("123456")
        composeRule.onNodeWithText("Iniciar sesión").performClick()

        composeRule.waitUntil(2_000) { viewModel.state.value.error != null }
        composeRule.onNodeWithText("El correo es obligatorio").assertIsDisplayed()
    }
}


