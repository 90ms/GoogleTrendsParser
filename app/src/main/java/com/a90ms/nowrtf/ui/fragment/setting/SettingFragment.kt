package com.a90ms.nowrtf.ui.fragment.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.a90ms.nowrtf.R
import com.a90ms.nowrtf.databinding.FragmentSettingBinding
import com.a90ms.nowrtf.ui.base.BaseFragment
import com.a90ms.nowrtf.util.Constants.PREF_THEME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val settingViewModel by viewModels<SettingViewModel>()

    private lateinit var preferences: SharedPreferences
    private var darkModeEnabled = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
        setupObserver()
    }

    private fun setupData() {
        binding.viewModel = settingViewModel
        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        settingViewModel.fetchTheme(preferences.getBoolean(PREF_THEME, false))
    }

    private fun setupObserver() {
        val owner = viewLifecycleOwner
        settingViewModel.run {
            clickState.observe(owner) {
                when (it) {
                    is ClickState.OnClickTheme -> {
                        darkModeEnabled = preferences.getBoolean(PREF_THEME, false)
                        darkModeEnabled = !darkModeEnabled
                        preferences.edit {
                            putBoolean(PREF_THEME, darkModeEnabled)
                        }
                        if (darkModeEnabled) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                        }

                        initClickState()
                        requireActivity().recreate()
                    }
                    is ClickState.OnClickSearchEngine -> {

                    }
                    is ClickState.Init -> {

                    }
                }
            }
        }
    }

}