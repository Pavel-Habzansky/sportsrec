package com.pavelhabzansky.sportsrec.features.record_list

sealed class RecordsListEvent {
    object NewRecordClick : RecordsListEvent()
    object FilterDismissed : RecordsListEvent()
    data class FilterOptionsSaved(
        val local: Boolean,
        val remote: Boolean
    ) : RecordsListEvent()

    data class ItemClicked(
        val id: String
    ) : RecordsListEvent()

    data class ItemDeleteClick(
        val id: String
    ): RecordsListEvent()
}

sealed class ControlBarEvent : RecordsListEvent() {
    object FilterClickEvent : ControlBarEvent()
    object UploadClickEvent : ControlBarEvent()
    object SynchronizeClickEvent : ControlBarEvent()
}