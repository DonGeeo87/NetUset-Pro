package dev.dongeeo.netuserspro.data.remote

import dev.dongeeo.netuserspro.domain.model.AuthSession
import kotlinx.coroutines.delay

class FakeAuthRemoteDataSource : AuthRemoteDataSource {
    override suspend fun login(email: String, password: String): AuthSession {
        delay(300) // simulate network latency
        if (email.equals("admin@netusers.com", ignoreCase = true) && password == "password123") {
            return AuthSession(email = email, token = "token-${email.hashCode()}")
        }
        throw IllegalArgumentException("Credenciales inválidas")
    }
}


