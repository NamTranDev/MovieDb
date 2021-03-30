package nam.tran.data.model

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("results") val reviews : MutableList<ReviewModel>
)