package io.architecture.feature.common.map

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import io.architecture.model.Route
import io.architecture.model.Trace

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun MapComposable(
    padding: PaddingValues,
    nodeTraces: Sequence<Trace>,
    displayRoute: Route?,
    onNodeClick: (String) -> Unit,
) {
   MapReplacement(nodeTraces, onNodeClick)
}