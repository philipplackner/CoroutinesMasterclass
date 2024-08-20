package com.plcoding.coroutinesmasterclass.sections.flows_in_practice.form_ui

import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn

class FormViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    val canRegister = email
        .debounce(500L)
        .combine(
            password,
        ) { email, password ->
            val isValidEmail = PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
            val isValidPassword = password.any { !it.isLetterOrDigit() } &&
                    password.length > 9

            isValidPassword && isValidEmail
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }
}