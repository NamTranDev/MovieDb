package nam.tran.moviedb.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import nam.tran.moviedb.di.module.UiModule
import nam.tran.moviedb.AppState
import nam.tran.moviedb.view.main.MainModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UiModule::class,
        MainModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<AppState> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
