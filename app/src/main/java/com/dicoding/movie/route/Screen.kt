package com.dicoding.movie.route

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
    object Detail : Screen("detail/{movieId}"){
        fun createRoute(movieId: Int): String {
            return "detail/$movieId"
        }
    }
}