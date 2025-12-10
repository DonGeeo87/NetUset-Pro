package dev.dongeeo.netuserspro.data.repository

import dev.dongeeo.netuserspro.data.remote.AuthRemoteDataSource
import dev.dongeeo.netuserspro.domain.model.AuthSession
import kotlinx.coroutines.CancellationException

class AuthRepository(
    private val remote: AuthRemoteDataSource
) {
    suspend fun login(email: String, password: String): Result<AuthSession> {
        return try {
            Result.success(remote.login(email, password))
        } catch (c: CancellationException) {
            throw c
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


