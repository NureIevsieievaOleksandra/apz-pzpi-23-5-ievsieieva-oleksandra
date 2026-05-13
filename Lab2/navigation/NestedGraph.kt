package ua.nure.smartlight.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NestedGraph {
    @Serializable data object Dashboard : NestedGraph()
    @Serializable data object Analytics : NestedGraph()
    @Serializable data object Settings : NestedGraph()
}