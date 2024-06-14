package com.example.mobproject.navigation

 sealed class Screens (val route : String) {
    object LoginScreen : Screens("login")
    object SignupScreen : Screens("signup")
     object SplashScreen : Screens("splash")
     object HomeScreen : Screens("home")
     object MapScreen : Screens("map")
     object HospitalScreen : Screens("hospital")
     object ProfileScreen : Screens("profile")
 }