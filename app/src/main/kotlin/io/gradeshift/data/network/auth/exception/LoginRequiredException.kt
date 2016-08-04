package io.gradeshift.data.network.auth.exception

// TODO humanized message "Automatic login failed, please try logging in again"
class LoginRequiredException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)