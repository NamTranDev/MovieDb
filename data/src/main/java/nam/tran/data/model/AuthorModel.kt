package nam.tran.data.model

import com.google.gson.annotations.SerializedName

data class AuthorModel(
    @SerializedName("username") val username : String,
    @SerializedName("avatar_path") val avarta : String
)