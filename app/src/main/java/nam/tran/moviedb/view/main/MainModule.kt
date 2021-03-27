package nam.tran.moviedb.view.main

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import nam.tran.data.di.DataModule

@Module(includes = [AndroidSupportInjectionModule::class, DataModule::class])
interface MainModule {

    @ContributesAndroidInjector
    fun bind(): MainActivity
}
