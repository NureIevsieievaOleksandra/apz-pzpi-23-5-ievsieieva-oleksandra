package ua.nure.smartlight.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Empty : Screen()

    @Serializable
    sealed class Auth : Screen() {
        @Serializable
        data object SignUp : Auth()

        @Serializable
        data object SignIn : Auth()
    }

    @Serializable
    data object Dashboard : Screen()

    @Serializable
    data object Analytics : Screen()

    @Serializable
    data object Settings : Screen()
}
