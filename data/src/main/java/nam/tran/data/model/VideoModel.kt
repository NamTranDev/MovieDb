package nam.tran.data.model

import com.google.gson.annotations.SerializedName

data class VideoModel(
    @SerializedName("id") val id : String,
    @SerializedName("key") val key : String
)