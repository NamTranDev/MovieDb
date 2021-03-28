package nam.tran.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results") val results : MutableList<MovieModel>
)