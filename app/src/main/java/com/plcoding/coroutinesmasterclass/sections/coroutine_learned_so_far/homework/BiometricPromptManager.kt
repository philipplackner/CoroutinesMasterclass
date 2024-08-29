package com.plcoding.coroutinesmasterclass.sections.coroutine_learned_so_far.homework
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class BiometricPromptManager(
    private val activity: AppCompatActivity
) {
    suspend fun showBiometricPrompt(
        title: String,
        description: String
    ): BiometricResult {
        return suspendCancellableCoroutine { continuation ->
            val manager = BiometricManager.from(activity)
            val authenticators = if (Build.VERSION.SDK_INT >= 30) {
                BIOMETRIC_STRONG or DEVICE_CREDENTIAL
            } else BIOMETRIC_STRONG

            val promptInfo = PromptInfo.Builder()
                .setTitle(title)
                .setDescription(description)
                .setAllowedAuthenticators(authenticators)

            if (Build.VERSION.SDK_INT < 30) {
                promptInfo.setNegativeButtonText("Cancel")
            }

            when (manager.canAuthenticate(authenticators)) {
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    continuation.resume(BiometricResult.HardwareUnavailable)
                }

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    continuation.resume(BiometricResult.FeatureUnavailable)
                }

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    continuation.resume(BiometricResult.AuthenticationNotSet)
                }

                else -> Unit
            }

            val prompt = BiometricPrompt(
                activity,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        continuation.resume(BiometricResult.AuthenticationError(errString.toString()))
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        continuation.resume(BiometricResult.AuthenticationSuccess)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        continuation.resume(BiometricResult.AuthenticationFailed)
                    }
                }
            )
            prompt.authenticate(promptInfo.build())
        }
    }
}
