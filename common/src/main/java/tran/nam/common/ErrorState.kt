package tran.nam.common

import android.util.MalformedJsonException
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException

// Handle Error
data class ErrorState(var message: String? = null, var code: Int? = 0,val errorResponse: JsonObject? = null) {

    companion object {
        fun getErrorMessage(t: Throwable): ErrorState {
            val code = when (t) {
                is SocketTimeoutException -> {
                    ErrorCode.SOCKET_TIMEOUT_EXCEPTION.code
                }
                is UnknownHostException -> {
                    ErrorCode.UNKNOWN_HOST_EXCEPTION.code
                }
                is SSLHandshakeException -> {
                    ErrorCode.SSL_HAND_SHAKE_EXCEPTION.code
                }
                is MalformedJsonException, is JsonSyntaxException -> {
                    ErrorCode.MALFORMED_JSON_EXCEPTION.code
                }
                is ParseException -> {
                    ErrorCode.PARSE_EXCEPTION.code
                }
                else -> {
                    ErrorCode.UNKNOW.code
                }
            }
            return ErrorState(code = code)
        }
    }
}