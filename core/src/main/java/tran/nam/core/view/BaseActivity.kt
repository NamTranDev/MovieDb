package tran.nam.core.view

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import tran.nam.core.R
import tran.nam.common.ErrorState
import tran.nam.common.Logger
import java.lang.Exception

abstract class BaseActivity : AppCompatActivity() {

    companion object {

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    private var mLoadingDialog: LoadingDialog? = null

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun layoutId(): Int

    protected open fun setStatusBar() {}


    protected open fun initLayout() {
        setContentView(layoutId())
    }

    /*
     * Init injection for activity
     **/
    protected open fun inject() {}

    open fun init(savedInstanceState: Bundle?) {}


    override fun onCreate(savedInstanceState: Bundle?) {
        setStatusBar()
        inject()
        super.onCreate(savedInstanceState)
        initLayout()
        mLoadingDialog = LoadingDialog(this)
        init(savedInstanceState)
    }

    fun showLoadingDialog() {
        hideKeyboard()
        mLoadingDialog?.run {
            if (!isShowing())
                showDialog()
        }
    }

    fun hideLoadingDialog() {
        mLoadingDialog?.run {
            if (isShowing())
                hideDialog()
        }
    }

    fun hideKeyboard() {
        currentFocus?.run {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    fun showKeyboard() {
        currentFocus?.run {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    open fun alert(
        error : ErrorState?,
        isFinish: Boolean = false,
        retry: (() -> Unit)? = null,
        dismissAlert: (() -> Unit)? = null
    ) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage(Html.fromHtml(error?.message ?: getString(R.string.error_unknown)).toString())
        alertDialog.setCancelable(false)
        alertDialog.setOnDismissListener {
            dismissAlert?.invoke()
        }
        if (retry == null){
            alertDialog.setPositiveButton(
                getString(R.string.text_ok)
            ) { dialog, _ ->
                run {
                    dialog.dismiss()
                    if (isFinish)
                        finish()
                }
            }
        }else{
            alertDialog.setNegativeButton(
                getString(R.string.text_cancel)
            ) { dialog, _ ->
                run {
                    dialog.dismiss()
                    if (isFinish)
                        finish()
                }
            }
            alertDialog.setPositiveButton(
                getString(R.string.text_retry)
            ) { dialog, _ ->
                run {
                    dialog.dismiss()
                    retry.invoke()
                }
            }
        }

        try {
            runOnUiThread {
                alertDialog.create().show()
            }
        }catch (e : Exception){
            Logger.debug(e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mLoadingDialog?.cancelDialog()
    }
}