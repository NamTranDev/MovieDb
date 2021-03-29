package nam.tran.moviedb.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import nam.tran.moviedb.view.home.HomeModule
import tran.nam.core.di.AppViewModelFactory
import javax.inject.Provider

@Module(
    includes = [
        HomeModule::class
    ]
)
class UiModule {

    @Provides
    fun provideViewModelFactory(
        providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory =
        AppViewModelFactory(providers)
}