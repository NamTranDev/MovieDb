package nam.tran.data.model

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(
    @SerializedName("results") val recommendations: MutableList<MovieModel>
)