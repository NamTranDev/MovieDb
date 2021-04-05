package nam.tran.data.model

import android.graphics.Color
import com.google.gson.annotations.SerializedName
import java.util.*

data class GenreModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    var colorRandom: Int? = null
) {
    fun randomColor() {
        val r = Random()
        val red: Int = r.nextInt(255 - 0 + 1) + 0
        val green: Int = r.nextInt(255 - 0 + 1) + 0
        val blue: Int = r.nextInt(255 - 0 + 1) + 0
        colorRandom = Color.rgb(red, green, blue)
    }
}