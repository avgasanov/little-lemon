package com.example.littlelemon

interface Destination {
    val route: String
}

object Home: Destination {
    override val route: String = "Home"
}

object Profile: Destination {
    override val route: String = "Profile"
}

object Onboarding: Destination {
    override val route: String = "Onboarding"
}