package com.pavelhabzansky.sportsrec.features.record_list

sealed class RecordsListScreenState {
    object Idle : RecordsListScreenState()
    object Filter : RecordsListScreenState()
    object Uploading : RecordsListScreenState()
    object Synchronizing : RecordsListScreenState()
}