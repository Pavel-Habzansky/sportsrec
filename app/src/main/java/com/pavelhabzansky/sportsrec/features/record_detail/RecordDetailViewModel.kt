package com.pavelhabzansky.sportsrec.features.record_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.pavelhabzansky.domain.core.Result
import com.pavelhabzansky.domain.features.record_detail.usecase.GetRecordByIdUseCase
import com.pavelhabzansky.domain.features.sports_records.usecase.DeleteRecordUseCase
import com.pavelhabzansky.sportsrec.R
import com.pavelhabzansky.sportsrec.core.BaseViewModel
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent
import com.pavelhabzansky.sportsrec.core.ui.UiText
import com.pavelhabzansky.sportsrec.features.record_detail.model.RecordDetail
import com.pavelhabzansky.sportsrec.features.record_detail.model.RecordDetailEvent
import com.pavelhabzansky.sportsrec.features.record_detail.model.toDetailScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecordById: GetRecordByIdUseCase,
    private val deleteRecord: DeleteRecordUseCase
) : BaseViewModel() {

    var record: RecordDetail? by mutableStateOf(null)
        private set

    init {
        val id = savedStateHandle.get<String>("recordId")
        id?.let {
            viewModelScope.launch {
                record = getRecordById(GetRecordByIdUseCase.Params(id)).toDetailScreen()
            }
        }
    }

    fun onEvent(event: RecordDetailEvent) {
        when (event) {
            is RecordDetailEvent.DeleteButtonClickedEvent -> {
                viewModelScope.launch {
                    val uiEvent = when (deleteRecord(DeleteRecordUseCase.Params(record!!.id))) {
                        is Result.Success -> UiEvent.NavigateUp
                        else -> UiEvent.Snackbar(UiText.ResourceText(R.string.record_delete_error))
                    }
                    _uiEvent.send(uiEvent)
                }
            }
        }
    }
}