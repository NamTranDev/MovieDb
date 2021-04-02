package nam.tran.moviedb.binding

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.Layout
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import at.blogc.android.views.ExpandableTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import nam.tran.data.model.CategoryModel
import nam.tran.data.model.MovieModel
import nam.tran.data.model.ReviewModel
import nam.tran.data.model.VideoModel
import nam.tran.moviedb.R
import nam.tran.moviedb.view.detail.CategoryAdapter
import nam.tran.moviedb.view.detail.MovieAdapter
import nam.tran.moviedb.view.detail.VideoAdapter
import tran.nam.common.ErrorCode
import tran.nam.common.Logger
import tran.nam.state.State


object Binding {

    @JvmStatic
    fun getErrorMessage(state: State?): String {
        return state?.errorState?.message ?: when (state?.errorState?.code) {
            ErrorCode.SOCKET_TIMEOUT_EXCEPTION.code -> "Không thể thiết lập kết nối đến máy chủ. Vui lòng kiểm tra lại mạng hoặc thử lại sau!"
            ErrorCode.UNKNOWN_HOST_EXCEPTION.code -> "Không thể tìm thấy máy chủ. Vui lòng kiểm tra cài đặt!"
            ErrorCode.SSL_HAND_SHAKE_EXCEPTION.code -> "Không thể tìm thấy máy chủ. Vui lòng kiểm tra cài đặt!"
            ErrorCode.MALFORMED_JSON_EXCEPTION.code -> "Không thể thiết lập kết nối đến máy chủ. Vui lòng kiểm tra lại mạng hoặc thử lại sau!"
            ErrorCode.PARSE_EXCEPTION.code -> "Có lỗi khi xử lý dữ liệu. Vui lòng thử lại sau!"
            else -> "Có lỗi khi kết nối. Vui lòng thao tác lại."
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["app:link","app:isBackground"], requireAll = false)
    fun loadImage(view: ImageView, link: String?,isBackground : Boolean = false) {
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

                val size = if (isBackground) "w500" else "w342"
                Glide.with(view.context).load("https://image.tmdb.org/t/p/$size$link")
                    .apply(reqOpt).into(view)
            } catch (e: Exception) {
                Logger.debug(e)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["app:videoId"], requireAll = false)
    fun loadThumbnail(view: ImageView, videoId: String?) {
        videoId?.run {
            if (videoId.isEmpty()) {
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

                Glide.with(view.context).load("http://img.youtube.com/vi/$videoId/hqdefault.jpg")
                    .apply(reqOpt).into(view)
            } catch (e: Exception) {
                Logger.debug(e)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["app:loadbackgroundColor"], requireAll = false)
    fun loadbackgroundColor(view: View, color: Int?) {
        color?.run {
            val draw = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(color, view.resources.getColor(android.R.color.transparent))
            )
            draw.setColor(color)
            draw.cornerRadius = view.resources.getDimension(R.dimen.positive_10dp)
            view.background = draw
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:listCategory","app:listVideo","app:listReview","app:recommendation"],
        requireAll = false
    )
    fun loadContent(
        rv: RecyclerView,
        listChannel: MutableList<CategoryModel>?,
        listVideo: MutableList<VideoModel>?,
        listReview: MutableList<ReviewModel>?,
        listRecommendation: MutableList<MovieModel>?
    ){
        val adapter = rv.adapter
        listChannel?.run {
            if (adapter is CategoryAdapter) {
                adapter.submitList(this)
            }
        }
        listVideo?.run {
            if (adapter is VideoAdapter) {
                adapter.submitList(this)
            }
        }
        listReview?.run {

        }
        listRecommendation?.run {
            if (adapter is MovieAdapter) {
                adapter.submitList(this)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:textExpand","app:readMore"],
        requireAll = false
    )
    fun expand(expand : ExpandableTextView,textExpand : String?,text : TextView){
        textExpand?.run {
            expand.text = textExpand
            expand.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    expand.viewTreeObserver.removeOnPreDrawListener(this)
                    expand.layout?.run {
                        val lines = lineCount
                        if (lines > 0 && getEllipsisCount(lines - 1) > 0)
                            text.visibility = View.VISIBLE
                    }
                    return true
                }
            });
        }
    }
}