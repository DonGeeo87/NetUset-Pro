package dev.dongeeo.netuserspro

import dev.dongeeo.netuserspro.domain.validation.CredentialsValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CredentialsValidatorTest {

    private val validator = CredentialsValidator()

    @Test
    fun `validate returns error when email empty`() {
        val result = validator.validate("", "password123")
        assertTrue(result.message?.contains("correo", ignoreCase = true) == true)
    }

    @Test
    fun `validate returns error when password too short`() {
        val result = validator.validate("user@test.com", "123")
        assertTrue(result.message?.contains("contraseña", ignoreCase = true) == true)
    }

    @Test
    fun `validate returns valid for correct credentials`() {
        val result = validator.validate("user@test.com", "password123")
        assertTrue(result.isValid)
        assertEquals(null, result.message)
    }
}


