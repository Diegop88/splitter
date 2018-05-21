package io.github.gianpamx.splitter.expense

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.github.gianpamx.splitter.core.Payer
import io.github.gianpamx.splitter.core.SavePayerUseCase
import javax.inject.Inject
import kotlin.math.truncate

class ExpenseViewModel @Inject constructor(private val savePayerUseCase: SavePayerUseCase) : ViewModel() {
    val payers = MutableLiveData<List<PayerModel>>()
    val error = MutableLiveData<Exception>()

    suspend fun save(payerModel: PayerModel) {
        try {
            val payers = savePayerUseCase.invoke(payerModel.toPayer())
            this.payers.postValue(payers.map {
                it.toPayModel()
            })
        } catch (e: Exception) {
            error.postValue(e)
        }
    }
}

private fun Payer.toPayModel() = PayerModel(
        id = id,
        name = name,
        amount = cents.toAmount()
)

private fun PayerModel.toPayer() = Payer(
        id = id,
        name = name,
        cents = amount.toCents()
)

private fun String.toCents() = try {
    truncate(toFloat() * 100).toInt()
} catch (t: Throwable) {
    0
}

private fun Int.toAmount() = if (this > 0) "$ ${this.toFloat() / 100f}" else ""
