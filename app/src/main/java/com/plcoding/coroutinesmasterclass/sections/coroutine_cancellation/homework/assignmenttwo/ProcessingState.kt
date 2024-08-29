package com.plcoding.coroutinesmasterclass.sections.coroutine_cancellation.homework.assignmenttwo

sealed interface ProcessingState {
    data object CheckingFunds : ProcessingState
    data object DebitingAccount : ProcessingState
    data object CreditingAccount : ProcessingState
    data object CleanupResources : ProcessingState
}