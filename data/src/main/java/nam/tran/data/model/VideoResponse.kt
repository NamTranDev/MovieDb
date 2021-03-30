package nam.tran.data.model

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("results") val videos : MutableList<VideoModel>
)