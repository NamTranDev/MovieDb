package tran.nam.core.viewmodel


import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import tran.nam.common.SingleLiveEvent
import tran.nam.state.Loading
import tran.nam.state.State
import tran.nam.common.ErrorResponse
import tran.nam.common.ErrorState
import tran.nam.common.Logger
import java.lang.ref.WeakReference

open class BaseViewModel : ViewModel(),
    LifecycleObserver {

    @Volatile
    var mViewLoadingWeakReference: WeakReference<IViewLifecycle>? = null

    val mCompositeDisposable = CompositeDisposable()

    // view state
    open val status = ObservableField<State?>()

    open var _loadingDialog = SingleLiveEvent<State?>()
    val loadingDialog: LiveData<State?>
        get() = _loadingDialog

    protected inline fun <reified V : IViewLifecycle> view(): V? {
        if (mViewLoadingWeakReference == null || mViewLoadingWeakReference?.get() == null)
            return null
        return V::class.java.cast(mViewLoadingWeakReference?.get())
    }

    open fun onInitialized(bundle: Bundle?, isRefresh: Boolean = false) {}
    open fun onResume(bundle: Bundle?) {}
    open fun onPause(bundle: Bundle?) {}

    fun <T> execute(
        obser: Observable<*>,
        onSuccess: ((T?) -> Unit)? = null,
        onMapper: ((Any) -> T)? = null,
        typeLoading: Int = Loading.LOADING_DIALOG,
        isRefresh: Boolean = false,
        isHideLoading: Boolean = true
    ) {
        addDisposable(obser.map {
            @Suppress("UNCHECKED_CAST")
            if (onMapper == null)
                it as T
            else
                onMapper.invoke(it)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                when (typeLoading) {
                    Loading.LOADING_NONE -> {
                    }
                    Loading.LOADING_DIALOG -> {
                        _loadingDialog.value = State.loading(loading = typeLoading)
                    }
                    Loading.LOADING_NORMAL -> {
                        status.set(State.loading(loading = typeLoading).apply {
                            hasRefresh = isRefresh
                        })
                    }
                }
            }.subscribe({
                when (typeLoading) {
                    Loading.LOADING_NONE -> {
                    }
                    Loading.LOADING_DIALOG -> {
                        if (isHideLoading)
                            _loadingDialog.value = State.success(loading = typeLoading)
                    }
                    Loading.LOADING_NORMAL -> {
                        status.set(State.success(loading = typeLoading).apply {
                            hasRefresh = isRefresh
                        })
                    }
                }
                Logger.debug(it)
                onSuccess?.invoke(it)
            }, {
                Logger.debug(it)
                val error = if (it is ErrorResponse) {
                    if (it.error != null) {
                        ErrorState.getErrorMessage(it.error!!)
                    } else {
                        ErrorState(it.messageError, it.code, it.responseError)
                    }
                } else {
                    ErrorState.getErrorMessage(it)
                }
                when (typeLoading) {
                    Loading.LOADING_NONE,
                    Loading.LOADING_DIALOG -> {
                        _loadingDialog.value = State.error(error, retry = {
                            execute(obser, onSuccess, onMapper, typeLoading)
                        }, loading = typeLoading)
                    }
                    Loading.LOADING_NORMAL -> {
                        if (isRefresh) {
                            _loadingDialog.value = State.error(error, retry = {
                                execute(obser, onSuccess, onMapper, typeLoading)
                            }, loading = typeLoading)
                        }
                        status.set(State.error(error, typeLoading, retry = {
                            execute(obser, onSuccess, onMapper, typeLoading, isRefresh)
                        }).apply {
                            hasRefresh = isRefresh
                        })
                    }
                }
            }))
    }

    fun onAttach(viewLoading: IViewLifecycle) {
        Logger.w("BaseViewModel : onAttach()")
        mViewLoadingWeakReference = WeakReference(viewLoading)
        viewLoading.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreated() {
        Logger.w("BaseViewModel : onCreated()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
        Logger.w("BaseViewModel : onStart()")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        Logger.w("BaseViewModel : onResume()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        Logger.w("BaseViewModel : onPause()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        Logger.w("BaseViewModel : onStop()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestroy() {
        Logger.w("BaseViewModel : onDestroy()")
        val viewWeakReference = this.mViewLoadingWeakReference
        if (viewWeakReference != null) {
            val view = viewWeakReference.get()
            view?.lifecycle?.removeObserver(this)
        }
    }

    fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}
