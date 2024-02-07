package io.architecture.playground.data.repository

import io.architecture.playground.data.local.LocalNodeRouteDataSource
import io.architecture.playground.data.mapping.toExternal
import io.architecture.playground.data.mapping.toLocal
import io.architecture.playground.data.remote.interfaces.NetworkDataSource
import io.architecture.playground.data.repository.interfaces.RouteRepository
import io.architecture.playground.di.DefaultDispatcher
import io.architecture.playground.di.IoDispatcher
import io.architecture.playground.model.Route
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultRouteRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localNodeRouteDataSource: LocalNodeRouteDataSource,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : RouteRepository {

    override suspend fun add(route: Route) = localNodeRouteDataSource.add(route.toLocal())

    override fun observeAndStoreRoutes() =
        networkDataSource.streamRoutes()
            .map { it.toExternal() }
            .flowOn(defaultDispatcher)
            .onEach { localNodeRouteDataSource.add(it.toLocal()) }
            .flowOn(ioDispatcher)

    override suspend fun getRouteBy(nodeId: String) = withContext(ioDispatcher) {
        localNodeRouteDataSource.getRouteBy(nodeId)?.toExternal()
    }
}