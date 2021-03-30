package nam.tran.data.di

import dagger.Binds
import dagger.Module
import nam.tran.data.api.NetModule
import nam.tran.data.interactor.UseCase
import nam.tran.data.interactor.IUseCase
import nam.tran.data.local.PreferenceModule
import javax.inject.Singleton


@Module(includes = [NetModule::class, PreferenceModule::class])
abstract class DataModule {

    @Binds
    @Singleton
    internal abstract fun provideHomeUseCase(usecase: UseCase): IUseCase
}
