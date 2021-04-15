package com.a90ms.nowrtf.ui.fragment.setting

sealed class ClickState {
    object Init: ClickState()
    object OnClickTheme : ClickState()
    object OnClickSearchEngine : ClickState()
}