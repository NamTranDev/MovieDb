package nam.tran.data.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val image: String,
    @SerializedName("backdrop_path") val background: String
)
