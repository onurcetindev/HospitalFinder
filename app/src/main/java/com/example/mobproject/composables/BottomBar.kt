package com.example.mobproject.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobproject.navigation.Screens

@Composable
fun BottomBar(navController: NavController? = null) {
    BottomAppBar(
    ) {
        NavigationBarItem(
            label = {
                Text(
                    text = "Home",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        letterSpacing = 0.sp
                    )
                )
            },
            selected = navController?.currentDestination?.route == Screens.HomeScreen.route,
            onClick = {
                if (navController?.currentDestination?.route != Screens.HomeScreen.route) {
                    navController?.navigate(Screens.HomeScreen.route) {
                        popUpTo(Screens.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = if (navController?.currentDestination?.route == Screens.HomeScreen.route) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home",
                    tint = Color.Black
                )
            },

            )
        NavigationBarItem(
            label = {
                Text(
                    text = "Map",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        letterSpacing = 0.sp
                    )
                )
            },
            selected = navController?.currentDestination?.route == Screens.MapScreen.route,
            onClick = {
                if (navController?.currentDestination?.route != Screens.MapScreen.route) {
                    navController?.navigate(Screens.MapScreen.route) {
                        popUpTo(Screens.MapScreen.route) {
                            inclusive = true
                        }
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = if (navController?.currentDestination?.route == Screens.MapScreen.route) Icons.Filled.Map else Icons.Outlined.Map,
                    contentDescription = "Map",
                    tint = Color.Black
                )
            },
        )
        NavigationBarItem(
            label = {
                Text(
                    text = "Hospital",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        letterSpacing = 0.sp
                    )
                )
            },
            selected = navController?.currentDestination?.route == Screens.HospitalScreen.route,
            onClick = {
                if (navController?.currentDestination?.route != Screens.HospitalScreen.route) {
                    navController?.navigate(Screens.HospitalScreen.route) {
                        popUpTo(Screens.HospitalScreen.route) {
                            inclusive = true
                        }
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = if (navController?.currentDestination?.route == Screens.HospitalScreen.route) Icons.Filled.LocalHospital else Icons.Outlined.LocalHospital,
                    contentDescription = "Hospital",
                    tint = Color.Black
                )
            },
        )
        NavigationBarItem(
            label = {
                Text(
                    text = "Profile",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        letterSpacing = 0.sp
                    )
                )
            },
            selected = navController?.currentDestination?.route == Screens.ProfileScreen.route,
            onClick = {
                if (navController?.currentDestination?.route != Screens.ProfileScreen.route) {
                    navController?.navigate(Screens.ProfileScreen.route) {
                        popUpTo(Screens.ProfileScreen.route) {
                            inclusive = true
                        }
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = if (navController?.currentDestination?.route == Screens.ProfileScreen.route) Icons.Filled.Person else Icons.Outlined.Person,
                    contentDescription = "Profile",
                    tint = Color.Black
                )
            },
        )
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    BottomBar()
}