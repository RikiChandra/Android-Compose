package com.dicoding.movie.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.movie.viewmodel.DetailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.movie.componet.UiState
import com.dicoding.movie.di.Injection
import com.dicoding.movie.model.Movie
import com.dicoding.movie.viewmodel.ViewModelFactory

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    movieId: Int,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(Injection.provideMovieRepository()) )
    ) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        state ->
        when(state){
            is UiState.Loading -> {
                viewModel.getMovieId(movieId)
            }
            is UiState.Success -> {
                val data = state.data
                Column {
                    TopAppBar(
                        elevation = 4.dp,
                        title = {
                            Text(
                                stringResource(id = com.dicoding.movie.R.string.app_name),
                                color = Color.White,
                            )
                        },
                        actions = {
                            IconButton(onClick = {

                            }) {
                                Text(
                                    text = "#%03d".format(movieId),
                                    color = Color.White,
                                )
                            }
                        }
                    )

                    MovieDetail(
                        movie = data,
                    )
                }
            }
            is UiState.Error -> {
                Text("Error")

            }
        }
    }


}
@Composable
fun MovieDetail(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        AsyncImage(
            model = movie.poster,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.genre,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.year.toString(),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}


@Preview(showBackground = true, )
@Composable
fun MovieDetailPreview() {
    MovieDetail(
        movie = Movie(
            id = 1,
            title = "Movie Name",
            genre = "Action",
            year = 2021,
            poster = "https://image.tmdb.org/t/p/w500/6KErczPBROQty7QoIsaa6wJYXZi.jpg"
        )
    )
}