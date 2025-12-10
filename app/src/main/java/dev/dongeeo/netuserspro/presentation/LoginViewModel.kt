package dev.dongeeo.netuserspro.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.dongeeo.netuserspro.data.repository.AuthRepository
import dev.dongeeo.netuserspro.domain.validation.CredentialsValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class LoginViewModel(
    private val repository: AuthRepository,
    private val validator: CredentialsValidator
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    fun onEmailChange(value: String) {
        _state.update { it.copy(email = value, error = null) }
    }

    fun onPasswordChange(value: String) {
        _state.update { it.copy(password = value, error = null) }
    }

    fun onLoginClick() {
        val current = _state.value
        val validation = validator.validate(current.email, current.password)
        if (!validation.isValid) {
            _state.update { it.copy(error = validation.message, isSuccess = false) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, isSuccess = false) }
            val result = repository.login(current.email, current.password)
            _state.update { it.copy(isLoading = false) }
            result.onSuccess {
                _state.update { state -> state.copy(isSuccess = true, error = null) }
            }.onFailure { throwable ->
                _state.update { state ->
                    state.copy(
                        error = throwable.message ?: "Error desconocido",
                        isSuccess = false
                    )
                }
            }
        }
    }

    fun resetSuccess() {
        _state.update { it.copy(isSuccess = false) }
    }
}


