package dev.dongeeo.netuserspro.domain.validation

data class ValidationResult(
    val isValid: Boolean,
    val message: String? = null
)

class CredentialsValidator {
    fun validate(email: String, password: String): ValidationResult {
        if (email.isBlank()) return ValidationResult(false, "El correo es obligatorio")
        if (!email.contains("@") || !email.contains(".")) {
            return ValidationResult(false, "Formato de correo inválido")
        }
        if (password.length < 6) return ValidationResult(false, "La contraseña debe tener 6+ caracteres")
        return ValidationResult(true)
    }
}


