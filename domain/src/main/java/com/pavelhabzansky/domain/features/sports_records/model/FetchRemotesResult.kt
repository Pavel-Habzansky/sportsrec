package com.pavelhabzansky.domain.features.sports_records.model

sealed class FetchRemotesResult {
    data class DownloadSuccess(val count: Int) : FetchRemotesResult()
    data class DownloadFailure(val throwable: Throwable? = null) : FetchRemotesResult()
    object NoNewData : FetchRemotesResult()
}