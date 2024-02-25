package io.architecture.core.di

import io.architecture.core.runtime.configuration.runtimeModule
import io.architecture.data.repositoryModule
import io.architecture.domain.di.useCaseModule
import io.architecture.network.websocket.imp.ktor.di.ktorClientModule
import io.architecture.network.websocket.imp.ktor.di.ktorModule
import io.architecture.network.websocket.imp.ktor.di.ktorServiceModule
import org.koin.dsl.module

// umbrella module
val coreKoinModules = module {
    includes(
        coroutineModule,
        runtimeModule,
        repositoryModule,
        useCaseModule,
        ktorModule,
        ktorClientModule,
        ktorServiceModule,
    )
}