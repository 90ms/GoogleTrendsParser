package com.a90ms.nowrtf.util

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import androidx.annotation.StringRes

class CommonDialog (
    @StringRes private val title: Int,
    @StringRes private val positiveBtn: Int,
    @StringRes private val negativeBtn: Int
) {
    fun commonDialog(
        context: Context,
        callback: (url: String) -> Unit
    ): AlertDialog {
        val editText = EditText(context)
        return AlertDialog.Builder(context)
            .setTitle(title)
            .setView(editText)
            .setNegativeButton(negativeBtn) { dialog, which ->
                dialog.dismiss()
            }.setPositiveButton(positiveBtn) { dialog, which ->
                callback(editText.text?.toString().orEmpty())
                dialog.dismiss()
            }.create()
    }
}