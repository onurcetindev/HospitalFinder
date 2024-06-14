import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobproject.composables.BottomBar
import com.example.mobproject.navigation.Screens
import com.example.mobproject.ui.screen.main.HomeScreen
import com.example.mobproject.ui.screen.main.HospitalScreen
import com.example.mobproject.ui.screen.auth.LoginScreen
import com.example.mobproject.ui.screen.main.MapScreen
import com.example.mobproject.ui.screen.main.ProfileScreen
import com.example.mobproject.ui.screen.auth.SignupScreen
import com.example.mobproject.ui.screen.splash.SplashScreen


@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),

    ) {
    NavHost(navController = navController, startDestination = Screens.LoginScreen.route) {

        composable(Screens.LoginScreen.route) {
            Scaffold(
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    LoginScreen(navController = navController)
                }
            }
        }

        composable(Screens.SignupScreen.route) {
            Scaffold() {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    SignupScreen(navController = navController)
                }
            }
        }
        composable(Screens.SplashScreen.route) {
            Scaffold() {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    SplashScreen(navController = navController)
                }
            }
        }
        composable(Screens.HomeScreen.route) {
            Scaffold(
                bottomBar = { BottomBar(navController = navController) },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    HomeScreen(navController = navController)
                }
            }
        }
        composable(Screens.MapScreen.route) {
            Scaffold(
                bottomBar = { BottomBar(navController = navController) },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    MapScreen(navController = navController)
                }
            }
        }
        composable(Screens.HospitalScreen.route) {
            Scaffold(
                bottomBar = { BottomBar(navController = navController) },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    HospitalScreen(navController = navController)
                }
            }
        }
        composable(Screens.ProfileScreen.route) {
            Scaffold(
                bottomBar = { BottomBar(navController = navController) },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    ProfileScreen(navController = navController)
                }
            }
        }
    }

}