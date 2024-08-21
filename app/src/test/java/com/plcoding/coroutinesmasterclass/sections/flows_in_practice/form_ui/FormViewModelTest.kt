@file:OptIn(ExperimentalCoroutinesApi::class)

package com.plcoding.coroutinesmasterclass.sections.flows_in_practice.form_ui

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.plcoding.coroutinesmasterclass.MainCoroutineRule
import com.plcoding.coroutinesmasterclass.TestDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FormViewModelTest {

    private lateinit var viewModel: FormViewModel
    private lateinit var testDispatchers: TestDispatchers

    @Before
    fun setUp() {
        testDispatchers = TestDispatchers()
        viewModel = FormViewModel(
            dispatchers = testDispatchers
        )
    }

    @Test
    fun testRegisterLoading_withCoroutine() = runTest(testDispatchers.testDispatcher) {
        assertThat(viewModel.isLoading.value).isFalse()

        viewModel.register()

        runCurrent()

        assertThat(viewModel.isLoading.value).isTrue()

        advanceUntilIdle()

        assertThat(viewModel.isLoading.value).isFalse()
    }

    @Test
    fun testRegisterLoading_withFlow() = runTest(testDispatchers.testDispatcher) {
        viewModel.isLoading.test {
            val initialEmission = awaitItem()
            assertThat(initialEmission).isFalse()

            viewModel.register()

            val loadingEmission = awaitItem()
            assertThat(loadingEmission).isTrue()

            val notLoadingEmission = awaitItem()
            assertThat(notLoadingEmission).isFalse()
        }
    }

    @Test
    fun testCanRegister() = runTest(testDispatchers.testDispatcher) {
        viewModel.canRegister.test {
            viewModel.onEmailChange("test@test.com")
            viewModel.onPasswordChange("test123456")
            assertThat(awaitItem()).isFalse()

            viewModel.onPasswordChange("test123456*")
            assertThat(awaitItem()).isTrue()
        }
    }
}