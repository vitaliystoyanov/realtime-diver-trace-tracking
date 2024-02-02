package io.architecture.playground.feature.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.architecture.playground.data.TraceRepository
import io.architecture.playground.data.remote.model.NetworkConnectionEvent
import io.architecture.playground.data.remote.model.NetworkConnectionEventType
import io.architecture.playground.model.Trace
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class DiverUiState(
    var latestTraces: List<Trace>,
    var tracesCount: Long,
    var connection: NetworkConnectionEvent
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val traceRepository: TraceRepository
) : ViewModel() {

    private var connection = traceRepository.getStreamConnectionEvents()
    private var countTraces = traceRepository.getStreamCountTraces()
    private var latestTracesByNodeIds = traceRepository.getStreamLatestTraceByUniqNodeIds()

    val uiState: StateFlow<DiverUiState> =
        combine(latestTracesByNodeIds, connection, countTraces) { traces, connection, count ->
            DiverUiState(traces, count, connection)
        }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DiverUiState(
                    emptyList(),
                    0,
                    NetworkConnectionEvent(NetworkConnectionEventType.ConnectionUndefined)
                )
            )
}