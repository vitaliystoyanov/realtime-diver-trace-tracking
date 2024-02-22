package io.architecture.network.websocket.imp.ktor.di

import io.architecture.network.websocket.api.RouteService
import io.architecture.network.websocket.api.RttService
import io.architecture.network.websocket.api.TraceService
import io.architecture.network.websocket.api.model.NetworkClientTime
import io.architecture.network.websocket.api.model.NetworkRoute
import io.architecture.network.websocket.api.model.NetworkServerTime
import io.architecture.network.websocket.api.model.NetworkTrace
import io.architecture.network.websocket.imp.ktor.KtorRouteService
import io.architecture.network.websocket.imp.ktor.KtorRttService
import io.architecture.network.websocket.imp.ktor.KtorTraceService
import io.architecture.network.websocket.imp.ktor.internal.KtorProtobufClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val ktorServiceModule = module {
    includes(ktorClientModule)

    single {
        KtorRttService(
            get<KtorProtobufClient<NetworkClientTime, NetworkServerTime>>(),
            get<CoroutineScope>(named("applicationScope")),       // TODO why it's default dispatcher
            get<CoroutineDispatcher>(named("defaultDispatcher")),
        )
    } bind RttService::class
    single {
        KtorTraceService(
            get<KtorProtobufClient<Any, NetworkTrace>>(),
            get<CoroutineScope>(named("applicationScope")),       // TODO why it's default dispatcher
            get<CoroutineDispatcher>(named("defaultDispatcher")),
        )
    } bind TraceService::class
    single {
        KtorRouteService(
            get<KtorProtobufClient<Any, NetworkRoute>>(),
            get<CoroutineScope>(named("applicationScope")),       // TODO why it's default dispatcher
            get<CoroutineDispatcher>(named("defaultDispatcher")),
        )
    } bind RouteService::class
}