package dev.dongeeo.netuserspro.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.foundation.text.KeyboardOptions
import dev.dongeeo.netuserspro.domain.model.User
import dev.dongeeo.netuserspro.presentation.components.ErrorState
import dev.dongeeo.netuserspro.presentation.components.LoadingState
import dev.dongeeo.netuserspro.presentation.components.UserCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserViewModel,
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    val users by viewModel.filteredUsers.collectAsStateWithLifecycle(emptyList())
    val loading by viewModel.loading.collectAsStateWithLifecycle(false)
    val error by viewModel.error.collectAsStateWithLifecycle(null)
    val query by viewModel.query.collectAsStateWithLifecycle("")

    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("NetUsers Pro") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                label = { Text("Buscar usuario") },
                placeholder = { Text("Nombre o email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
            )

            val swipeState = rememberSwipeRefreshState(isRefreshing = loading)

            SwipeRefresh(
                state = swipeState,
                onRefresh = { viewModel.loadUsers() }
            ) {
                Crossfade(
                    targetState = Triple(loading, error, users),
                    label = "list_states"
                ) { (isLoading, errorMessage, list) ->
                    when {
                        isLoading -> LoadingState()
                        errorMessage != null -> ErrorState(
                            message = errorMessage,
                            onRetry = { viewModel.loadUsers() }
                        )
                        else -> UserListContent(
                            users = list,
                            onUserClick = onUserClick,
                            contentPadding = PaddingValues(bottom = 24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UserListContent(
    users: List<User>,
    onUserClick: (User) -> Unit,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(users) { user ->
            UserCard(
                user = user,
                onClick = { onUserClick(user) }
            )
        }
    }
}

