package com.a90ms.nowrtf.util

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import androidx.annotation.StringRes

class CommonDialog(
    private val title: String,
    @StringRes private val positiveBtn: Int,
    @StringRes private val negativeBtn: Int
) {
    fun commonDialog(
        context: Context,
        callback: () -> Unit
    ): AlertDialog {
        return AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog)
            .setTitle(title)
            .setNegativeButton(negativeBtn) { dialog, which ->
                dialog.dismiss()
            }.setPositiveButton(positiveBtn) { dialog, which ->
                callback()
                dialog.dismiss()
            }.create()
    }
}