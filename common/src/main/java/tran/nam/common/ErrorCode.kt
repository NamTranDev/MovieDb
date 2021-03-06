package tran.nam.common

enum class ErrorCode(val code: Int) {
    SOCKET_TIMEOUT_EXCEPTION(995),
    UNKNOWN_HOST_EXCEPTION(994),
    SSL_HAND_SHAKE_EXCEPTION(993),
    MALFORMED_JSON_EXCEPTION(992),
    PARSE_EXCEPTION(991),
    UNKNOW(990),
    EMPTY_INPUT(989),
    EMPTY_PHONE(986),
    VALID_PHONE(980),
    EMPTY_OTP(984),
    VALID_OTP(979),
    EMPTY_USER(988),
    EMPTY_PASSWORD(987),
}