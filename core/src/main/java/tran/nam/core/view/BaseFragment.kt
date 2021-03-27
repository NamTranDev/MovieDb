/*
 * Copyright 2017 Vandolf Estrellado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tran.nam.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import tran.nam.core.R
import tran.nam.common.ErrorState
import tran.nam.common.Logger

abstract class BaseFragment : Fragment() {

    private var mViewCreated = false
    private var mViewDestroyed = false

    private var mWaitThread: WaitThread? = null

    protected abstract val layoutId: Int

    open fun initLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.debug("Lifecycle - onCreateView ${javaClass.name}")
        val view = initLayout(inflater, container)
        initToolbar(arguments)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.debug("Lifecycle - onViewCreated ${javaClass.name}")
        if (isWaitProgress()) {
            view.postDelayed({
                onInitialized(arguments, false)
            }, timeDelay())
        } else {
            mViewCreated = true
            mViewDestroyed = false
            mWaitThread?.continueProcessing()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Logger.debug("Lifecycle - onActivityCreated ${javaClass.name}")
    }

    open fun initToolbar(bundle: Bundle? = null) {}

    open fun timeDelay(): Long {
        return resources.getInteger(R.integer.animation_time_full).toLong()
    }

    open fun isWaitProgress(): Boolean {
        return true
    }

    fun isViewCreated(): Boolean {
        return mViewCreated
    }

    open fun onInitialized(bundle: Bundle? = null, isRefresh: Boolean = false) {}

    protected fun showLoadingDialog() {
        activity?.run {
            if (this is BaseActivity && !isFinishing)
                showLoadingDialog()
        }
    }

    protected fun hideLoadingDialog() {
        activity?.run {
            if (this is BaseActivity && !isFinishing)
                hideLoadingDialog()
        }
    }

    protected fun showKeyboard() {
        activity?.run {
            if (this is BaseActivity && !isFinishing)
                showKeyboard()
        }
    }

    protected fun hideKeyboard() {
        activity?.run {
            if (this is BaseActivity && !isFinishing)
                hideKeyboard()
        }
    }

    fun showAlert(
        error: ErrorState? = null,
        isFinish: Boolean = false, retry: (() -> Unit)? = null,
        dismissListener: (() -> Unit)? = null
    ) {
        activity?.run {
            if (this is BaseActivity && !isFinishing)
                alert(error, isFinish, retry, dismissListener)
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        var animation = super.onCreateAnimation(transit, enter, nextAnim)
//        Logger.debug("Animation Fragment : $this")
//        Logger.debug("Animation Fragment - onCreateAnimation : $animation")
//        Logger.debug("Animation Fragment - nextAnim : $nextAnim")
        if (enter) {
            if (animation == null && nextAnim != 0) {
                animation = AnimationUtils.loadAnimation(activity, nextAnim);
            }

            view?.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            animation?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    view?.setLayerType(View.LAYER_TYPE_NONE, null);
                    if (mViewDestroyed)
                        return
//                    Logger.debug("Animation Fragment : " + "onAnimationEnd")
//                    Logger.debug("Animation Fragment : $mWaitThread")
                    if (mWaitThread == null) {
                        mWaitThread = WaitThread(this@BaseFragment)
                        mWaitThread?.start()
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }

        return animation
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.debug("Lifecycle - onDestroy ${javaClass.name}")
        mWaitThread?.stopProcessing()
        mViewDestroyed = true
        mViewCreated = false
    }
}