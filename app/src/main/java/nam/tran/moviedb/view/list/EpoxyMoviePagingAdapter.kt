package nam.tran.moviedb.view.list

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import nam.tran.data.model.MovieModel
import nam.tran.moviedb.MovieListBindingModel_
import tran.nam.state.State

class EpoxyMoviePagingAdapter constructor(private val goToDetail: (MovieModel) -> Unit) :
    PagedListEpoxyController<MovieModel>() {

    private var state: State? = null

    override fun buildItemModel(currentPosition: Int, item: MovieModel?): EpoxyModel<*> {
        return MovieListBindingModel_().item(item)
            .id(item?.id)
            .click(View.OnClickListener {
                item?.run(goToDetail)
            }) /*?: return NetworkStateBindingModel_().id("state").state(state)*/
    }

//    override fun addModels(models: List<EpoxyModel<*>>) {
//        if (state?.isSuccess() == true) {
//            super.addModels(models.distinct())
//        } else {
//            super.addModels(
//                models.plus(
//                    NetworkStateBindingModel_().id("state").state(state)
//                ).distinct()
//            )
//        }
//    }
}