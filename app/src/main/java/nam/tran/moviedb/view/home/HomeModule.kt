package nam.tran.moviedb.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import tran.nam.core.di.inject.ViewModelKey

@Module(includes = [HomeModule.ProvideViewModel::class])
abstract class HomeModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun bind(): HomeFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        fun provideViewModel(): ViewModel =
            HomeViewModel()
    }

    @Module
    class InjectViewModel {
        @Provides
        fun provideViewModel(
            factory: ViewModelProvider.Factory,
            target: HomeFragment
        ) =
            ViewModelProviders.of(target, factory).get(HomeViewModel::class.java)
    }
}
