package dev.dongeeo.netuserspro

import dev.dongeeo.netuserspro.data.repository.AuthRepository
import dev.dongeeo.netuserspro.domain.model.AuthSession
import dev.dongeeo.netuserspro.domain.validation.CredentialsValidator
import dev.dongeeo.netuserspro.presentation.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelAuthTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var repository: AuthRepository

    private val validator = CredentialsValidator()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = LoginViewModel(repository, validator)
    }

    @Test
    fun `onLoginClick sets success state when repository returns session`() = runTest {
        whenever(repository.login("user@test.com", "password123"))
            .thenReturn(Result.success(AuthSession("user@test.com", "token")))

        viewModel.onEmailChange("user@test.com")
        viewModel.onPasswordChange("password123")
        viewModel.onLoginClick()
        advanceUntilIdle()

        assertTrue(viewModel.state.value.isSuccess)
        assertNull(viewModel.state.value.error)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onLoginClick shows validation error when credentials invalid`() = runTest {
        viewModel.onEmailChange("wrongemail")
        viewModel.onPasswordChange("123")

        viewModel.onLoginClick()

        assertEquals("Formato de correo inválido", viewModel.state.value.error)
        verify(repository, never()).login("wrongemail", "123")
    }

    @Test
    fun `onLoginClick shows error when repository fails`() = runTest {
        whenever(repository.login("user@test.com", "password123"))
            .thenReturn(Result.failure(IllegalArgumentException("Credenciales inválidas")))

        viewModel.onEmailChange("user@test.com")
        viewModel.onPasswordChange("password123")
        viewModel.onLoginClick()
        advanceUntilIdle()

        assertEquals("Credenciales inválidas", viewModel.state.value.error)
        assertFalse(viewModel.state.value.isSuccess)
    }
}


