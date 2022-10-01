package com.pavelhabzansky.sportsrec.features.record_list

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.pavelhabzansky.domain.features.sports_records.model.SportsRecord
import com.pavelhabzansky.domain.features.sports_records.usecase.FetchSportsRecordsUseCase
import com.pavelhabzansky.domain.features.sports_records.usecase.GetSportsRecordsUseCase
import com.pavelhabzansky.sportsrec.core.BaseViewModel
import com.pavelhabzansky.sportsrec.core.navigation.Route
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsListViewModel @Inject constructor(
    private val getSportsRecords: GetSportsRecordsUseCase,
    private val fetchSportsRecords: FetchSportsRecordsUseCase
) : BaseViewModel() {

    var sportsRecords = emptyFlow<List<SportsRecord>>()
        private set

    init {
        sportsRecords = getSportsRecords()
    }

    fun onResume() {
        viewModelScope.launch {
            fetchSportsRecords()
        }
    }

    fun onEvent(event: RecordsListEvent) {
        when (event) {
            is RecordsListEvent.NewRecordClick -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _uiEvent.send(UiEvent.Navigate(Route.NEW_RECORD))
                }
            }
        }
    }

}