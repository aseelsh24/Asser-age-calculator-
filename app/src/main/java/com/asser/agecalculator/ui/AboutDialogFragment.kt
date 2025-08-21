package com.asser.agecalculator.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.asser.agecalculator.BuildConfig
import com.asser.agecalculator.R
import com.asser.agecalculator.databinding.DialogAboutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AboutDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogAboutBinding.inflate(LayoutInflater.from(context))

        val versionName = BuildConfig.VERSION_NAME
        val versionCode = BuildConfig.VERSION_CODE
        binding.tvVersion.text = getString(R.string.version, versionName, versionCode)

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.about_title)
            .setView(binding.root)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }
}
