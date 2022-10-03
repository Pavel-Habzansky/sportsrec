package com.pavelhabzansky.sportsrec.features.record_detail.model

sealed class RecordDetailEvent {
    object DeleteButtonClickedEvent : RecordDetailEvent()
}