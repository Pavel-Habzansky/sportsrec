package com.pavelhabzansky.sportsrec.features.record_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.pavelhabzansky.domain.features.sports_records.model.FetchRemotesResult
import com.pavelhabzansky.domain.features.sports_records.model.FilterOptions
import com.pavelhabzansky.domain.features.sports_records.model.UploadLocalsResult
import com.pavelhabzansky.domain.features.sports_records.usecase.FetchSportsRecordsUseCase
import com.pavelhabzansky.domain.features.sports_records.usecase.FilterRecordsUseCase
import com.pavelhabzansky.domain.features.sports_records.usecase.GetSportsRecordsUseCase
import com.pavelhabzansky.domain.features.sports_records.usecase.UploadLocalRecordsUseCase
import com.pavelhabzansky.sportsrec.R
import com.pavelhabzansky.sportsrec.core.BaseViewModel
import com.pavelhabzansky.sportsrec.core.navigation.Route
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent
import com.pavelhabzansky.sportsrec.core.preferences.FilterPreferences
import com.pavelhabzansky.sportsrec.core.ui.UiText
import com.pavelhabzansky.sportsrec.features.record_list.model.RecordListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsListViewModel @Inject constructor(
    private val getSportsRecords: GetSportsRecordsUseCase,
    private val fetchSportsRecords: FetchSportsRecordsUseCase,
    private val filterRecordsUseCase: FilterRecordsUseCase,
    private val uploadLocalRecords: UploadLocalRecordsUseCase,
    private val filterPreferences: FilterPreferences
) : BaseViewModel() {

    var screenState by mutableStateOf<RecordsListScreenState>(RecordsListScreenState.Idle)
        private set

    var sportsRecords = emptyFlow<List<RecordListItem>>()
        private set

    var filterOptions: FilterOptions = filterPreferences.loadFilterOptions()
        private set

    init {
        loadSportsRecords()
    }

    fun onResume() {
        viewModelScope.launch {
            fetchSportsRecords()
        }
    }

    private fun loadSportsRecords() {
        sportsRecords = getSportsRecords()
            .map {
                val filtered = filterRecordsUseCase(FilterRecordsUseCase.Params(it, filterOptions))
                filtered.toListItems()
            }
    }

    fun onEvent(event: RecordsListEvent) {
        when (event) {
            is RecordsListEvent.NewRecordClick -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _uiEvent.send(UiEvent.Navigate(Route.NEW_RECORD))
                }
            }
            is RecordsListEvent.FilterDismissed -> screenState = RecordsListScreenState.Idle
            is RecordsListEvent.FilterOptionsSaved -> {
                filterOptions = filterOptions.copy(
                    local = event.local,
                    remote = event.remote
                )
                filterPreferences.storeFilterOptions(filterOptions)
                loadSportsRecords()
            }
            is ControlBarEvent -> handleControlBarEvent(event)
        }
    }

    private fun handleControlBarEvent(event: ControlBarEvent) {
        when (event) {
            is ControlBarEvent.FilterClickEvent -> {
                screenState = RecordsListScreenState.Filter
            }
            is ControlBarEvent.UploadClickEvent -> {
                viewModelScope.launch {
                    screenState = RecordsListScreenState.Uploading
                    val snackTextRes = when (uploadLocalRecords()) {
                        is UploadLocalsResult.UploadSuccessful -> R.string.upload_success
                        is UploadLocalsResult.UploadFailure -> R.string.upload_failure
                        is UploadLocalsResult.NoDataToUpload -> R.string.upload_no_locals
                    }
                    _uiEvent.send(UiEvent.Snackbar(UiText.ResourceText(snackTextRes)))
                    screenState = RecordsListScreenState.Idle
                }
            }
            is ControlBarEvent.SynchronizeClickEvent -> {
                viewModelScope.launch {
                    screenState = RecordsListScreenState.Synchronizing
                    val snackTextRes = when (fetchSportsRecords()) {
                        is FetchRemotesResult.DownloadSuccess -> R.string.download_success
                        is FetchRemotesResult.NoNewData -> R.string.download_no_new_data
                        is FetchRemotesResult.DownloadFailure -> R.string.download_failure
                    }
                    _uiEvent.send(UiEvent.Snackbar(UiText.ResourceText(snackTextRes)))
                    screenState = RecordsListScreenState.Idle
                }
            }
        }
    }
}