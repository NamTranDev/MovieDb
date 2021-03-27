package nam.tran.data.di

import dagger.Module
import nam.tran.data.api.NetModule
import nam.tran.data.database.DbModule
import nam.tran.data.local.PreferenceModule


@Module(includes = [NetModule::class, /*DbModule::class,*/ PreferenceModule::class])
abstract class DataModule {

}
