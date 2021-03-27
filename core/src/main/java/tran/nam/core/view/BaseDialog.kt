package tran.nam.core.view

import android.app.Dialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.graphics.Point
import android.os.Bundle
import androidx.annotation.LayoutRes
import android.view.*
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import tran.nam.core.R

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class BaseDialog<T : ViewDataBinding> : androidx.fragment.app.DialogFragment() {

    protected abstract fun bidingData(biding: T)

    @LayoutRes
    protected abstract fun layoutID(): Int

    open val isFullScreen = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(cancelOnTouch())
        dialog.window?.attributes?.windowAnimations = R.style.Dialog
        attrFun(dialog)
        return dialog
    }

    open fun cancelOnTouch(): Boolean {
        return true
    }

    open fun isCancel(): Boolean {
        return true
    }

    open fun attrFun(dialog : Dialog){}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val biding = DataBindingUtil.inflate<T>(inflater,
                layoutID(), container, false)

        bidingData(biding)
        return biding.root
    }

    override fun onResume() {
        // Store access variables for window and blank point
        val window = dialog?.window
        if (window != null && !isFullScreen) {
            val size = Point()
            // Store dimensions of the screen in `size`
            val display = window.windowManager.defaultDisplay
            display.getSize(size)
            // Set the width of the dialog proportional to 75% of the screen width
            if (maxWidth() != 0f && maxHeight() != 0f) {
                window.setLayout((size.x * maxWidth()).toInt(), (size.y * maxHeight()).toInt())
            } else if (maxWidth() != 0f) {
                window.setLayout((size.x * maxWidth()).toInt(), WRAP_CONTENT)
            } else if (maxHeight() != 0f) {
                window.setLayout(WRAP_CONTENT, (size.y * maxHeight()).toInt())
            } else {
                window.setLayout(WRAP_CONTENT, WRAP_CONTENT)
            }
            window.setGravity(Gravity.CENTER)
            // Call super onResume after sizing

        }
        super.onResume()
    }

    open fun maxHeight(): Float {
        return 0f
    }

    open fun maxWidth(): Float {
        return 0.8f
    }
}
