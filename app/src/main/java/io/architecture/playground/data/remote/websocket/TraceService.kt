package io.architecture.playground.data.remote.websocket

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.architecture.playground.data.remote.model.NetworkClientTime
import io.architecture.playground.data.remote.model.NetworkServerTime
import io.architecture.playground.data.remote.model.NetworkTrace
import io.architecture.playground.data.remote.websocket.scarlet.Target
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow

interface TraceService {

    @Receive
    @Target(type = "")
    fun streamConnection(): ReceiveChannel<WebSocket.Event>

    @Receive
    @Target(type = "trace")
    fun streamTraces(): ReceiveChannel<NetworkTrace>

    @Send
    fun sendClientTime(time: NetworkClientTime)

    @Receive
    @Target(type = "rtt")
    fun streamServerTime(): ReceiveChannel<NetworkServerTime>
}