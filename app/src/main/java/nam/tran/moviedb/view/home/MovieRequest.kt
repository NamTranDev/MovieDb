package nam.tran.moviedb.view.home

data class MovieRequest(
    val type: String,
    val isHome : Boolean = true,
    val refresh : Boolean = false
)