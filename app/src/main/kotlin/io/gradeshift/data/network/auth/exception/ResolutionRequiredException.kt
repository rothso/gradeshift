package io.gradeshift.data.network.auth.exception

import com.google.android.gms.common.api.Status

class ResolutionRequiredException(val status: Status, message: String? = null, cause: Throwable? = null) : Exception(message, cause)