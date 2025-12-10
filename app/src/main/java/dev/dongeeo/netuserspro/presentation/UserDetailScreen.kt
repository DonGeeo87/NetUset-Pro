package dev.dongeeo.netuserspro.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dongeeo.netuserspro.domain.model.User

@Composable
fun UserDetailScreen(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = user.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Text(text = user.email, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.padding(top = 4.dp))
        Text(
            text = "ID: ${user.id}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

