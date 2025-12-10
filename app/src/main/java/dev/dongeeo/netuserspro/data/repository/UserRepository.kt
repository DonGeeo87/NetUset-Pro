package dev.dongeeo.netuserspro.data.repository

import dev.dongeeo.netuserspro.data.remote.ApiService
import dev.dongeeo.netuserspro.domain.model.User
import kotlinx.coroutines.CancellationException

class UserRepository(
    private val api: ApiService
    // Room cache could be injected here in futuras iteraciones
) {
    suspend fun fetchUsers(): Result<List<User>> {
        return try {
            val users = api.getUsers()
            Result.success(users)
        } catch (c: CancellationException) {
            throw c
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

