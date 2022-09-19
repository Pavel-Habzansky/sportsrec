package com.pavelhabzansky.sportsrec.features.record_list

sealed class RecordsListEvent {
    object NewRecordClick : RecordsListEvent()
}