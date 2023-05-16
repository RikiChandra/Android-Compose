package com.dicoding.movie.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.movie.route.Screen
import com.dicoding.movie.screen.AboutScreen
import com.dicoding.movie.screen.DetailScreen
import com.dicoding.movie.screen.HomeScreen

@Composable
fun MovieApp(
    navController: NavHostController = rememberNavController()
) {

    NavHost(navController = navController,
        startDestination = Screen.Home.route){
        composable(route = Screen.Home.route){
            HomeScreen(
                navigateToDetail = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                },
                navController = navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ){
            val movieId = it.arguments?.getInt("movieId")?:0
            DetailScreen(movieId = movieId)

        }
        composable(Screen.About.route){
            AboutScreen(navigateUp = { navController.navigateUp() })
        }

    }

}
@Preview(showBackground = true)
@Composable
fun MovieAppPreview() {
    MovieApp()
}