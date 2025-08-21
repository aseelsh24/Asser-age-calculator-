package com.asser.agecalculator.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.asser.agecalculator.model.AgeResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test calculate age simple case`() = runTest {
        viewModel.setBirthDate(1990, 1, 1)
        viewModel.setEndDate(2022, 1, 1)
        viewModel.calculateAge()

        val result = viewModel.ageResult.value
        assertNotNull(result)
        assertEquals(32, result?.years)
        assertEquals(0, result?.months)
        assertEquals(0, result?.days)
        assertEquals(11688, result?.totalDays)
    }

    @Test
    fun `test calculate age with leap year`() = runTest {
        viewModel.setBirthDate(2000, 2, 28)
        viewModel.setEndDate(2001, 3, 1)
        viewModel.calculateAge()

        val result = viewModel.ageResult.value
        assertNotNull(result)
        assertEquals(1, result?.years)
        assertEquals(0, result?.months)
        assertEquals(1, result?.days) // 2000 is a leap year, so Feb has 29 days
    }

    @Test
    fun `test end date before birth date posts error`() = runTest {
        viewModel.setBirthDate(2022, 1, 1)
        viewModel.setEndDate(2020, 1, 1)
        viewModel.calculateAge()

        assertNull(viewModel.ageResult.value)
        assertNotNull(viewModel.error.value)
        assertEquals("End date cannot be before birth date.", viewModel.error.value)
    }

    @Test
    fun `test end date same as birth date`() = runTest {
        viewModel.setBirthDate(2022, 1, 1)
        viewModel.setEndDate(2022, 1, 1)
        viewModel.calculateAge()

        val result = viewModel.ageResult.value
        assertNotNull(result)
        assertEquals(0, result?.years)
        assertEquals(0, result?.months)
        assertEquals(0, result?.days)
        assertEquals(0, result?.totalDays)
    }

    @Test
    fun `test calculate age for one day difference`() = runTest {
        viewModel.setBirthDate(2022, 1, 1)
        viewModel.setEndDate(2022, 1, 2)
        viewModel.calculateAge()

        val result = viewModel.ageResult.value
        assertNotNull(result)
        assertEquals(0, result?.years)
        assertEquals(0, result?.months)
        assertEquals(1, result?.days)
        assertEquals(1, result?.totalDays)
        assertEquals(24, result?.totalHours)
        assertEquals(1440, result?.totalMinutes)
    }

    @Test
    fun `test null dates posts error`() = runTest {
        viewModel.calculateAge()
        assertNotNull(viewModel.error.value)
    }
}
