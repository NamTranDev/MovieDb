/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tran.nam.state

import android.annotation.SuppressLint
import tran.nam.common.ErrorState
import tran.nam.state.Status.*

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
</T> */
@Suppress("unused")
class State(
    @Status val status: Int, val errorState: ErrorState?, @Loading var loading: Int,
    val retry: (() -> Unit)?
) {

    var initial = true
    var hasRefresh = false
    var displayErrorDialog = false

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val resource = other as State?

        return (status == resource!!.status
                && (if (errorState != null) errorState == resource.errorState else resource.errorState == null))
    }

    override fun hashCode(): Int {
        var result = status
        result = 31 * result + (errorState?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + getStatus(status) + "\n" +
                "loading=" + getLoading(loading) + "\n" +
                ", message='" + errorState + '\''.toString() +
                '}'.toString()
    }

    private fun getStatus(@Status status: Int): String {
        when (status) {
            ERROR -> return "ErrorState"
            LOADING -> return "Loading"
            SUCCESS -> return "Success"
        }
        return ""
    }

    @SuppressLint("SwitchIntDef")
    private fun getLoading(@Loading loading: Int): String {
        when (loading) {
            Loading.LOADING_DIALOG -> return "Loading Dialog"
            Loading.LOADING_NONE -> return "Loading None"
            Loading.LOADING_NORMAL -> return "Loading Normal"
        }
        return ""
    }

    fun isSuccess(): Boolean {
        return status == SUCCESS
    }

    fun isLoading(): Boolean {
        return status == LOADING
    }

    fun isLoadingInitial() : Boolean{
        return status == LOADING && initial
    }

    companion object {

        @JvmStatic
        fun success(loading: Int = Loading.LOADING_NORMAL): State {
            return State(SUCCESS, null, loading, null)
        }

        @JvmStatic
        fun successPaging(loading: Int = Loading.LOADING_NORMAL): State {
            val resource = State(SUCCESS, null, loading, null)
            resource.initial = false
            return resource
        }

        @JvmStatic
        fun error(
            msg: ErrorState?,
            loading: Int = Loading.LOADING_NORMAL,
            retry: (() -> Unit)? = null
        ): State {
            return State(ERROR, msg, loading, retry)
        }

        @JvmStatic
        fun errorPaging(
            msg: ErrorState?,
            loading: Int = Loading.LOADING_NORMAL,
            retry: () -> Unit
        ): State {
            val resource = State(ERROR, msg, loading, retry)
            resource.initial = false
            return resource
        }

        @JvmStatic
        fun loading(@Loading loading: Int = Loading.LOADING_NORMAL,hasRefresh : Boolean = false): State {
            val state = State(LOADING, null, loading, null)
            state.hasRefresh = hasRefresh
            return state
        }

        @JvmStatic
        fun loadingPaging(@Loading loading: Int = Loading.LOADING_NORMAL): State {
            val resource = State(LOADING, null, loading, null)
            resource.initial = false
            return resource
        }
    }
}
