package io.architecture.playground.ext

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.time.Duration

fun <T> Flow<T>.chunked(chunkSize: Int): Flow<List<T>> {
    val buffer = mutableListOf<T>()
    return flow {
        this@chunked.collect {
            buffer.add(it)
            if (buffer.size == chunkSize) {
                emit(buffer.toList())
                buffer.clear()
            }
        }
        if (buffer.isNotEmpty()) {
            emit(buffer.toList())
        }
    }
}

fun <T> Flow<T>.chunkedSetBy(maxSize: Int, interval: Duration, predicate: (T) -> String) =
    channelFlow {

        val buffer = mutableSetOf<T>()
        var flushJob: Job? = null

        collect { value ->

            flushJob?.cancelAndJoin()
            val alreadyBuffered = buffer.find { predicate(it) == predicate(value) }
            if (alreadyBuffered == null) buffer.add(value)
//            buffer.add(value)

            if (buffer.size >= maxSize) {
                ensureActive()
                send(buffer.toSet())
                buffer.clear()
            } else {
                flushJob = launch {
                    delay(interval)
                    if (buffer.isNotEmpty()) {
                        ensureActive()
                        send(buffer.toSet())
                        buffer.clear()
                    }
                }
            }
        }

        flushJob?.cancelAndJoin()

        if (buffer.isNotEmpty()) {
            send(buffer.toSet())
            buffer.clear()
        }
    }