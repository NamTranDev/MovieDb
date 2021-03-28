package nam.tran.moviedb.binding

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import tran.nam.common.Logger

object Binding {

    @JvmStatic
    @BindingAdapter(value = ["app:link"], requireAll = false)
    fun loadImage(view: ImageView, link: String?) {
        link?.run {
            if (link.isEmpty()) {
                return@run
            }
            try {
                val circularProgressDrawable = CircularProgressDrawable(view.context)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.setColorSchemeColors(Color.WHITE)
                circularProgressDrawable.start()

                val reqOpt = RequestOptions
                    .diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(circularProgressDrawable)

                Glide.with(view.context).load("https://image.tmdb.org/t/p/w342" + link)
                    .apply(reqOpt).into(view)
            } catch (e: Exception) {
                Logger.debug(e)
            }
        }
    }
}