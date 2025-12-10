package dev.dongeeo.netuserspro.data.remote

import dev.dongeeo.netuserspro.domain.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}

