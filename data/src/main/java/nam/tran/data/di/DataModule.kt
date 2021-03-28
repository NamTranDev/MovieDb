package nam.tran.data.di

import dagger.Binds
import dagger.Module
import nam.tran.data.api.NetModule
import nam.tran.data.interactor.HomeUseCase
import nam.tran.data.interactor.IHomeUseCase
import nam.tran.data.local.PreferenceModule
import javax.inject.Singleton


@Module(includes = [NetModule::class, PreferenceModule::class])
abstract class DataModule {

    @Binds
    @Singleton
    internal abstract fun provideHomeUseCase(usecase: HomeUseCase): IHomeUseCase
}
