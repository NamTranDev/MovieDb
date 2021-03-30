package nam.tran.data.model

import com.google.gson.annotations.SerializedName

data class ReviewModel(
    @SerializedName("id") val id: String,
    @SerializedName("author_details") val author: AuthorModel,
    @SerializedName("content") val content: String,
    @SerializedName("updated_at") val time: String
)