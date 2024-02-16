package io.architecture.playground.data.repository.interfaces

import io.architecture.playground.model.Connection
import io.architecture.playground.model.UpstreamRtt
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

interface ConnectionStateRepository {

    fun streamRoundTripTime(interval: Duration = 1.seconds): Flow<UpstreamRtt>

    fun streamConnectionEvents(): Flow<Connection>

}