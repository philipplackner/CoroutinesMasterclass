package com.plcoding.coroutinesmasterclass.sections.coroutine_learned_so_far.homework

sealed interface BiometricResult {
    data object HardwareUnavailable: BiometricResult
    data object FeatureUnavailable: BiometricResult
    data class AuthenticationError(val error: String): BiometricResult
    data object AuthenticationFailed: BiometricResult
    data object AuthenticationSuccess: BiometricResult
    data object AuthenticationNotSet: BiometricResult
}