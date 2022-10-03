package com.pavelhabzansky.domain.features.sports_records.model

sealed class UploadLocalsResult {
    data class UploadSuccessful(
        val count: Int
    ) : UploadLocalsResult()

    data class UploadFailure(
        val throwable: Throwable? = null
    ) : UploadLocalsResult()

    object NoDataToUpload : UploadLocalsResult()
}
