package tran.nam.core.viewmodel

import tran.nam.common.ErrorState

interface IViewLoading : IViewLifecycle {

    fun showDialogLoading()

    fun hideDialogLoading()

    fun onShowDialogError(
        error: ErrorState?,
        isFinish: Boolean = false, retry: (() -> Unit)? = null
    )
}
