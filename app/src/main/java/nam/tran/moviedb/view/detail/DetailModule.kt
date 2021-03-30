package nam.tran.moviedb.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import nam.tran.data.interactor.IUseCase
import tran.nam.core.di.inject.ViewModelKey

@Module(includes = [DetailModule.ProvideViewModel::class])
abstract class DetailModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun bind(): DetailFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(DetailViewModel::class)
        fun provideViewModel(usecase : IUseCase): ViewModel =
            DetailViewModel(usecase)
    }

    @Module
    class InjectViewModel {
        @Provides
        fun provideViewModel(
            factory: ViewModelProvider.Factory,
            target: DetailFragment
        ) =
            ViewModelProviders.of(target, factory).get(DetailViewModel::class.java)
    }
}