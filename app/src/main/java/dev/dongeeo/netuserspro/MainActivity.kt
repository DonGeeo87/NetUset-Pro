package dev.dongeeo.netuserspro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.dongeeo.netuserspro.data.remote.FakeAuthRemoteDataSource
import dev.dongeeo.netuserspro.data.remote.RetrofitClient
import dev.dongeeo.netuserspro.data.repository.AuthRepository
import dev.dongeeo.netuserspro.data.repository.UserRepository
import dev.dongeeo.netuserspro.domain.validation.CredentialsValidator
import dev.dongeeo.netuserspro.presentation.LoginScreen
import dev.dongeeo.netuserspro.presentation.LoginViewModel
import dev.dongeeo.netuserspro.presentation.UserViewModel
import dev.dongeeo.netuserspro.presentation.navigation.NetUsersNavHost
import dev.dongeeo.netuserspro.presentation.theme.NetUsersProTheme

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = AuthRepository(FakeAuthRemoteDataSource())
                val validator = CredentialsValidator()
                return LoginViewModel(repository, validator) as T
            }
        }
    }

    private val viewModel: UserViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = UserRepository(RetrofitClient.api)
                return UserViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetUsersProTheme {
                val loginState = loginViewModel.state.collectAsStateWithLifecycle()
                var isAuthenticated by rememberSaveable { mutableStateOf(false) }

                if (loginState.value.isSuccess) {
                    isAuthenticated = true
                }

                if (isAuthenticated) {
                    NetUsersNavHost(viewModel = viewModel)
                } else {
                    LoginScreen(
                        viewModel = loginViewModel,
                        onLoginSuccess = { isAuthenticated = true }
                    )
                }
            }
        }
    }
}