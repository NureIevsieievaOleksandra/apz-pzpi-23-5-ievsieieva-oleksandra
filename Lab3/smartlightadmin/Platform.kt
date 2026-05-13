package ua.nure.smartlightadmin

interface Platform {
    val name: String
    val type: PlatformType
}

enum class PlatformType {
    Android, JVM, WEB
}

expect fun getPlatform(): Platform