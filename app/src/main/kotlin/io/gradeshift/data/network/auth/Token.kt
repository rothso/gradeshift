package io.gradeshift.data.network.auth

import org.threeten.bp.Instant
import org.threeten.bp.temporal.ChronoUnit

data class Token(val value: String, val expiry: Instant) {

    companion object {
        val DUMMY_TOKEN = Token("", Instant.now().plus(30, ChronoUnit.MINUTES))
    }
}