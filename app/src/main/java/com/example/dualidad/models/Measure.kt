package com.example.dualidad.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Measure(
    val url: String,
    val title: String,
    val desc: String
) : Parcelable
