package dev.dongeeo.netuserspro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.dongeeo.netuserspro.data.remote.RetrofitClient
import dev.dongeeo.netuserspro.data.repository.UserRepository
import dev.dongeeo.netuserspro.presentation.UserViewModel
import dev.dongeeo.netuserspro.presentation.navigation.NetUsersNavHost
import dev.dongeeo.netuserspro.presentation.theme.NetUsersProTheme

class MainActivity : ComponentActivity() {

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
                NetUsersNavHost(viewModel = viewModel)
            }
        }
    }
}