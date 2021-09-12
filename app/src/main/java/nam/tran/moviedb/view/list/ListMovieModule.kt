package nam.tran.moviedb.view.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import nam.tran.data.interactor.IUseCase
import tran.nam.core.di.inject.ViewModelKey

@Module(includes = [ListMovieModule.ProvideViewModel::class])
abstract class ListMovieModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun bind(): ListMovieFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(ListMovieViewModel::class)
        fun provideViewModel(useCase : IUseCase): ViewModel =
            ListMovieViewModel(useCase)
    }

    @Module
    class InjectViewModel {
        @Provides
        fun provideViewModel(
            factory: ViewModelProvider.Factory,
            target: ListMovieFragment
        ) =
            ViewModelProviders.of(target, factory).get(ListMovieViewModel::class.java)
    }
}
