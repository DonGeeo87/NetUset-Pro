package dev.dongeeo.netuserspro.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.dongeeo.netuserspro.data.repository.UserRepository
import dev.dongeeo.netuserspro.domain.model.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _users = MutableStateFlow(emptyList<User>())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _filteredUsers = MutableStateFlow(emptyList<User>())
    val filteredUsers: StateFlow<List<User>> = _filteredUsers.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private var loadJob: Job? = null

    fun loadUsers() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _error.value = null
            _loading.value = true
            val result = repository.fetchUsers()
            _loading.value = false

            result.onSuccess { list ->
                _users.value = list
                applyFilter(_query.value.orEmpty(), list)
            }.onFailure { e ->
                _error.value = e.message ?: "Unknown error"
            }
        }
    }

    fun onSearchQueryChange(value: String) {
        _query.value = value
        applyFilter(value, _users.value.orEmpty())
    }

    private fun applyFilter(value: String, base: List<User>) {
        if (value.isBlank()) {
            _filteredUsers.value = base
            return
        }
        val normalized = value.trim().lowercase()
        _filteredUsers.value = base.filter { user ->
            user.name.lowercase().contains(normalized) ||
                user.email.lowercase().contains(normalized)
        }
    }
}

