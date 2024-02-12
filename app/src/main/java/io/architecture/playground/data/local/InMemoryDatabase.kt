package io.architecture.playground.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.architecture.playground.data.local.convertors.DateTypeConvertor
import io.architecture.playground.data.local.convertors.ListCoordinatesTypeConvertor
import io.architecture.playground.data.local.dao.NodeDao
import io.architecture.playground.data.local.dao.RouteDao
import io.architecture.playground.data.local.dao.TraceDao
import io.architecture.playground.data.local.model.NodeEntity
import io.architecture.playground.data.local.model.RouteEntity
import io.architecture.playground.data.local.model.TraceEntity

@Database(
    entities = [NodeEntity::class, TraceEntity::class, RouteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListCoordinatesTypeConvertor::class, DateTypeConvertor::class)
abstract class InMemoryDatabase : RoomDatabase() {

    abstract fun nodeDao(): NodeDao

    abstract fun traceDao(): TraceDao

    abstract fun routeDao(): RouteDao
}