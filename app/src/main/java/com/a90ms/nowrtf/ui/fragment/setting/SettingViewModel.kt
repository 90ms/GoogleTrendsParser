package com.a90ms.nowrtf.ui.fragment.setting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel @ViewModelInject constructor(
) : ViewModel() {

    private val _clickState = MutableLiveData<ClickState>()
    val clickState: LiveData<ClickState> get() = _clickState

    private val _txtTheme = MutableLiveData("")
    val txtTheme: LiveData<String> get() = _txtTheme

    private val _txtSearchEngine = MutableLiveData("")
    val txtSearchEngine: LiveData<String> get() = _txtSearchEngine

    fun initClickState() {
        _clickState.value = ClickState.Init
    }

    fun fetchTheme(isDarkMode: Boolean) {
        _txtTheme.value = if (isDarkMode) {
            "테마변경(Light)"
        } else {
            "테마변경(Dark)"
        }
    }

    fun onClickTheme() {
        _clickState.value = ClickState.OnClickTheme
    }

    fun onClickSearchEngine() {
        _clickState.value = ClickState.OnClickSearchEngine
    }

}