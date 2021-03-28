package nam.tran.data.model

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres") val genres: MutableList<GenreModel>
)