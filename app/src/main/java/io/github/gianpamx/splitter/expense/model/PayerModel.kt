package io.github.gianpamx.splitter.expense.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PayerModel(
        var id: Long = 0,
        var name: String = "",
        var amount: Double = 0.0
) : Parcelable
