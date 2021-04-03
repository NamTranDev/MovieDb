package nam.tran.data.model

import com.google.gson.annotations.SerializedName
import tran.nam.common.Logger
import java.text.SimpleDateFormat
import java.util.*

data class MovieDetailModel(
    @SerializedName("id") val id: Long,
    @SerializedName("poster_path") val image: String,
    @SerializedName("backdrop_path") val background: String,
    @SerializedName("overview") val description: String,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val time: String,
    @SerializedName("genres") val categorys: MutableList<CategoryModel>,
    var videos: MutableList<VideoModel>?,
    var reviews: List<ReviewModel>?,
    var recommendations: List<MovieModel>?
) {
    fun getDate(): String {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val formatter = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
        try {
            return formatter.format(parser.parse(time))
        } catch (e: Exception) {
            Logger.debug(e)
        }
        return ""
    }
}