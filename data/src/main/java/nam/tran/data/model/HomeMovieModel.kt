package nam.tran.data.model

data class HomeMovieModel(
    val movieTrendings : MutableList<MovieModel>,
    val genres : MutableList<GenreModel>,
    val moviePopulars : MutableList<MovieModel>,
    val movieTopRateds : MutableList<MovieModel>,
    val movieUpComings : MutableList<MovieModel>
)