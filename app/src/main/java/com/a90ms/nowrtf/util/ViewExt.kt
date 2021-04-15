package com.a90ms.nowrtf.util

import android.view.View
import androidx.navigation.NavController


fun View.gone(){
    this.visibility= View.GONE
}

fun View.show(){
    this.visibility= View.VISIBLE
}

fun NavController.isValidDestination(destination: Int): Boolean {
    return destination == this.currentDestination!!.id
}