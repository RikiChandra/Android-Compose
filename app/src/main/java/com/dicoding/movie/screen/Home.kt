package com.dicoding.movie.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dicoding.movie.componet.MovieListItem
import com.dicoding.movie.componet.SearchBar
import com.dicoding.movie.componet.UiState
import com.dicoding.movie.di.Injection
import com.dicoding.movie.model.Movie
import com.dicoding.movie.viewmodel.MainViewModel
import com.dicoding.movie.viewmodel.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = ViewModelFactory(Injection.provideMovieRepository())),
    navigateToDetail: (Int) -> Unit,
    navController: NavController = rememberNavController()
) {
    Column{
        TopAppBar(
            title = { Text(text = "Movie App") },
            backgroundColor = Color.Blue,
            contentColor = Color.White,
            actions = {
                IconButton(onClick = { navController.navigate("favorite")

                }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
                }
            }

        )

        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
            state ->
            when(state){
               is UiState.Loading -> {
                   viewModel.getMovie()
               }
                is UiState.Success -> {
                    HomeContent(
                        movieData = state.data,
                        navigateToDetail = navigateToDetail,
                        modifier = Modifier.fillMaxWidth(),
                        viewModel = viewModel
                    )
                }
                is UiState.Error -> {
                    Text(text = state.error.message.toString())
                }
            }
        }

    }

}

@Composable
fun HomeContent(
    movieData: List<Movie>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    viewModel: MainViewModel = viewModel(factory = ViewModelFactory(Injection.provideMovieRepository())),
) {
    val query by viewModel.searchQuery
    Box(modifier = modifier) {
        LazyColumn {
            item {
                SearchBar(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            items(movieData, key = { it.id }) { film ->
                MovieListItem(
                    name = film.title,
                    photoUrl = film.poster,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigateToDetail(film.id)
                        }
                )
            }
        }
    }
}
