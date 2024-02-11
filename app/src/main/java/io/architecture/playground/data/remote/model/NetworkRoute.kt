package io.architecture.playground.data.remote.model

import com.google.gson.annotations.SerializedName

data class NetworkRoute(
    @SerializedName("n") var nodeId: String,
    @SerializedName("r") var route: Array<DoubleArray>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NetworkRoute

        if (nodeId != other.nodeId) return false
        if (route != null) {
            if (other.route == null) return false
            if (!route.contentDeepEquals(other.route)) return false
        } else if (other.route != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nodeId.hashCode()
        result = 31 * result + (route?.contentDeepHashCode() ?: 0)
        return result
    }
}