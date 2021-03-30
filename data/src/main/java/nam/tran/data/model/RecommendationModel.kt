package nam.tran.data.model

import com.google.gson.annotations.SerializedName

data class RecommendationModel(
    @SerializedName("id") val id: Long,
    @SerializedName("original_title") val title: String,
    @SerializedName("poster_path") val image: String
)