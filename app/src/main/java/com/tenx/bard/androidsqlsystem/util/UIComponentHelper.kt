package com.tenx.bard.androidsqlsystem.util

import android.graphics.Color
import android.widget.EditText

fun editTextToTextView(view: EditText) {
    view.isEnabled = false
    view.isCursorVisible = false
    view.setBackgroundColor(Color.TRANSPARENT)
    view.keyListener = null
}