package dev.dongeeo.netuserspro.domain.model

/**
 * Represents a successful authentication session.
 */
data class AuthSession(
    val email: String,
    val token: String
)


