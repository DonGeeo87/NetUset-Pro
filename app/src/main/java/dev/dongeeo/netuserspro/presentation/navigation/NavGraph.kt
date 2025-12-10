package dev.dongeeo.netuserspro.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.dongeeo.netuserspro.presentation.UserDetailScreen
import dev.dongeeo.netuserspro.presentation.UserListScreen
import dev.dongeeo.netuserspro.presentation.UserViewModel

private const val LIST_ROUTE = "list"
private const val DETAIL_ROUTE = "detail/{userId}"

@Composable
fun NetUsersNavHost(
    viewModel: UserViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = LIST_ROUTE
    ) {
        composable(LIST_ROUTE) {
            UserListScreen(
                viewModel = viewModel,
                onUserClick = { user ->
                    navController.navigate("detail/${user.id}")
                }
            )
        }
        composable(DETAIL_ROUTE) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
            val user by viewModel.users.collectAsStateWithLifecycle()
            val selected = user.firstOrNull { it.id == userId }
            selected?.let {
                UserDetailScreen(user = it)
            }
        }
    }
}

