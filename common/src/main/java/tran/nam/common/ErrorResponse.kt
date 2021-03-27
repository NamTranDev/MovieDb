package tran.nam.common

import com.google.gson.JsonObject
import java.lang.RuntimeException

open class ErrorResponse constructor(
    val messageError: String? = null,
    val code: Int?,
    val error: Throwable? = null,
    val responseError: JsonObject? = null
) : RuntimeException() {

    override fun toString(): String {
        return "ErrorResponse(code=$code, messageError=$messageError)"
    }
}