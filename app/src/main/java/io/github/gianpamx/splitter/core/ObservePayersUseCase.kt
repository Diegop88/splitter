package io.github.gianpamx.splitter.core

import io.github.gianpamx.splitter.core.model.Payer

interface ObservePayersUseCase {

    fun invoke(expenseId: Long, observer: (List<Payer>, Int) -> Unit)
}

class ObservePayersUseCaseImpl(private val persistenceGateway: PersistenceGateway) : ObservePayersUseCase {
    override fun invoke(expenseId: Long, observer: (List<Payer>, Int) -> Unit) {
        persistenceGateway.observePayments(expenseId, {
            var total = it.sumBy { it.cents }
            observer.invoke(it, total)
        })
    }
}
