package nam.tran.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailModel(
    @SerializedName("id") val id: Long,
    @SerializedName("poster_path") val image: String,
    @SerializedName("backdrop_path") val background: String,
    @SerializedName("overview") val description: String,
    @SerializedName("original_title") val title: String,
    @SerializedName("genres") val genres: MutableList<GenreModel>,
    var videos : MutableList<VideoModel>?,
    var reviews : MutableList<ReviewModel>?,
    var recommendations : MutableList<RecommendationModel>?
)