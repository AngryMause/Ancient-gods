package com.ui.presentation.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ui.presentation.about.AboutScreen
import com.ui.presentation.games.GameScreen
import com.ui.presentation.menu.MenuScreen
import com.ui.presentation.menu.MenuScreenEvent
import com.ui.presentation.option.OptionScreen
import com.ui.presentation.splash.SplashScreen
import com.ui.presentation.splash.SplashScreenEvent
import com.util.SetOrientationPortrait

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    SetOrientationPortrait()
    NavHost(navController = navController, startDestination = SplashScreen) {
        composable<OptionScreen> {
            OptionScreen { navController.popBackStack() }
        }
        composable<GameScreen> {
            GameScreen {
                navController.popBackStack()
            }
        }
        composable<AboutScreen> {
            AboutScreen { navController.popBackStack() }
        }
        composable<SplashScreen> {
            SplashScreen {
                when (it) {
                    is SplashScreenEvent.OpenMenu -> navController.navigate(MenuScreen) {
                        popUpTo(SplashScreen) { inclusive = true }
                    }
                }
            }
        }
        composable<MenuScreen> {
            MenuScreen {
                when (it) {
                    is MenuScreenEvent.StartGame -> navController.navigate(GameScreen)
                    is MenuScreenEvent.Option -> navController.navigate(OptionScreen)
                    is MenuScreenEvent.About -> navController.navigate(AboutScreen)
                }
            }
        }
    }
}


