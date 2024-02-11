package io.architecture.playground.data.mapping

import com.tinder.scarlet.WebSocket
import io.architecture.playground.data.local.model.TraceEntity
import io.architecture.playground.data.remote.model.ConnectionEvent
import io.architecture.playground.data.remote.model.NetworkTrace
import io.architecture.playground.model.Trace
import java.util.Date

fun WebSocket.Event.toExternal(): ConnectionEvent = when (this) {
    is WebSocket.Event.OnConnectionOpened<*> -> ConnectionEvent.OPENED
    is WebSocket.Event.OnConnectionClosed -> ConnectionEvent.CLOSED
    is WebSocket.Event.OnConnectionClosing -> ConnectionEvent.CLOSING
    is WebSocket.Event.OnConnectionFailed -> ConnectionEvent.FAILED
    is WebSocket.Event.OnMessageReceived -> ConnectionEvent.MESSAGE_RECEIVED
}

fun Trace.toLocal() = TraceEntity(
    id = id,
    nodeId = nodeId,
    lon = lon,
    timestamp = time,
    speed = speed,
    azimuth = azimuth,
    alt = alt,
    lat = lat,
)

fun TraceEntity.toExternal() = Trace(
    id = id,
    nodeId = nodeId,
    lon = lon,
    time = timestamp,
    speed = speed,
    azimuth = azimuth,
    alt = alt,
    lat = lat,
)

fun NetworkTrace.toLocal() = TraceEntity(
    id = 0,
    nodeId = nodeId,
    lon = lon,
    timestamp = Date(time),
    speed = speed,
    azimuth = azimuth,
    alt = alt,
    lat = lat,
)

fun TraceEntity.toNetwork() = NetworkTrace(
    nodeId = nodeId,
    lon = lon,
    time = timestamp.time,
    speed = speed,
    azimuth = azimuth,
    alt = alt,
    lat = lat,
)


fun Trace.toNetwork() = toLocal().toNetwork()

fun NetworkTrace.toExternal() = toLocal().toExternal()

fun List<TraceEntity>.toNetwork() = map(TraceEntity::toNetwork)

fun List<Trace>.toLocal() = map(Trace::toLocal)


@JvmName("localToExternal")
fun List<TraceEntity>.toExternal() = map(TraceEntity::toExternal)

@JvmName("networkToLocal")
fun List<NetworkTrace>.toLocal() = map(NetworkTrace::toLocal)

@JvmName("externalToNetwork")
fun List<Trace>.toNetwork() = map(Trace::toNetwork)

@JvmName("networkToExternal")
fun List<NetworkTrace>.toExternal() = map(NetworkTrace::toExternal)


fun TraceEntity.assignProperties(tracePooled: Trace, source: TraceEntity): Trace =
    tracePooled.apply {
        id = source.id
        nodeId = source.nodeId
        lon = source.lon
        time = source.timestamp
        speed = source.speed
        azimuth = source.azimuth
        alt = source.alt
        lat = source.lat
    }

fun Trace.assignProperties(tracePooled: TraceEntity, source: Trace): TraceEntity =
    tracePooled.apply {
        id = source.id
        nodeId = source.nodeId
        lon = source.lon
        timestamp = source.time
        speed = source.speed
        azimuth = source.azimuth
        alt = source.alt
        lat = source.lat
    }


fun NetworkTrace.assignProperties(
    tracePooled: TraceEntity, source: NetworkTrace
): TraceEntity =
    tracePooled.apply {
        id = 0
        nodeId = source.nodeId
        lon = source.lon
        timestamp = Date(source.time)
        speed = source.speed
        azimuth = source.azimuth
        alt = source.alt
        lat = source.lat
    }