package com.asser.agecalculator.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.asser.agecalculator.R
import com.asser.agecalculator.databinding.ActivityMainBinding
import com.asser.agecalculator.viewmodel.MainViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import android.content.Intent
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupClickListeners()
        observeViewModel()

        if (savedInstanceState == null) {
            viewModel.setEndDateToToday()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                AboutDialogFragment().show(supportFragmentManager, "AboutDialog")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupClickListeners() {
        binding.etDateOfBirth.setOnClickListener {
            showDatePicker(isBirthDate = true)
        }

        binding.etCalculateToDate.setOnClickListener {
            showDatePicker(isBirthDate = false)
        }

        binding.btnCalculate.setOnClickListener {
            viewModel.calculateAge()
        }

        binding.btnShare.setOnClickListener {
            shareResult()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.birthDate.collect { date ->
                        date?.let {
                            binding.etDateOfBirth.setText(formatDate(it))
                        }
                    }
                }

                launch {
                    viewModel.endDate.collect { date ->
                        date?.let {
                            binding.etCalculateToDate.setText(formatDate(it))
                        }
                    }
                }

                launch {
                    viewModel.ageResult.collect { result ->
                        if (result != null) {
                            binding.cardResult.visibility = View.VISIBLE
                            binding.tvAgeResult.text = getString(
                                R.string.years_months_days,
                                result.years,
                                result.months,
                                result.days
                            )
                            binding.tvTotalDaysResult.text = result.totalDays.toString()
                            binding.tvTotalHoursResult.text = result.totalHours.toString()
                            binding.tvTotalMinutesResult.text = result.totalMinutes.toString()
                        } else {
                            binding.cardResult.visibility = View.GONE
                        }
                    }
                }

                launch {
                    viewModel.error.collect { error ->
                        error?.let {
                            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                            viewModel.clearError()
                        }
                    }
                }
            }
        }
    }

    private fun showDatePicker(isBirthDate: Boolean) {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date))
            .build()

        picker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = Instant.ofEpochMilli(selection)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            if (isBirthDate) {
                viewModel.setBirthDate(selectedDate.year, selectedDate.monthValue, selectedDate.dayOfMonth)
            } else {
                viewModel.setEndDate(selectedDate.year, selectedDate.monthValue, selectedDate.dayOfMonth)
            }
        }

        picker.show(supportFragmentManager, picker.toString())
    }

    private fun formatDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return date.format(formatter)
    }

    private fun shareResult() {
        val result = viewModel.ageResult.value ?: return

        val ageText = getString(R.string.years_months_days, result.years, result.months, result.days)
        val shareText = getString(
            R.string.result_share_text,
            ageText,
            result.totalDays.toString(),
            result.totalHours.toString(),
            result.totalMinutes.toString()
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share)))
    }
}
