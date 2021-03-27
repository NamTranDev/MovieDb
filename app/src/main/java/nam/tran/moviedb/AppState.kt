package nam.tran.moviedb

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import nam.tran.moviedb.di.component.DaggerApplicationComponent
import tran.nam.common.Logger

class AppState : DaggerApplication(), Application.ActivityLifecycleCallbacks{

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return
            }
            LeakCanary.install(this)
        }
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        Logger.enter("onActivityCreated : " + activity.componentName)
    }

    override fun onActivityStarted(activity: Activity) {
        Logger.enter("onActivityStarted : " + activity.componentName)
    }

    override fun onActivityResumed(activity: Activity) {
        Logger.enter("onActivityResumed : " + activity.componentName)
    }

    override fun onActivityPaused(activity: Activity) {
        Logger.enter("onActivityPaused : " + activity.componentName)
    }

    override fun onActivityStopped(activity: Activity) {
        Logger.enter("onActivityStopped : " + activity.componentName)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {
        Logger.enter("onActivitySaveInstanceState : " + activity.componentName)
    }

    override fun onActivityDestroyed(activity: Activity) {
        Logger.enter("onActivityDestroyed : " + activity.componentName)
    }
}