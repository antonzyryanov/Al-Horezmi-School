package com.alhorezmi.school.presentation.viewmodel

import com.alhorezmi.school.domain.model.PlayerRecord
import com.alhorezmi.school.domain.usecase.GetPlayerRecordBookUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RecordsUiState(
    val records: List<PlayerRecord> = emptyList(),
)

class RecordsViewModel(
    private val getPlayerRecordBookUseCase: GetPlayerRecordBookUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(RecordsUiState())
    val uiState: StateFlow<RecordsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = RecordsUiState(records = getPlayerRecordBookUseCase().records)
        }
    }
}
