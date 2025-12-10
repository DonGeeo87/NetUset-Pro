package dev.dongeeo.netuserspro.data.remote

import dev.dongeeo.netuserspro.domain.model.AuthSession

interface AuthRemoteDataSource {
    suspend fun login(email: String, password: String): AuthSession
}


