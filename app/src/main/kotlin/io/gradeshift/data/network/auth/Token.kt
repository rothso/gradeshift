package io.gradeshift.data.network.auth

import java.util.*

data class Token(val value: Int, val expires: Calendar) {

    companion object {
        val DUMMY_TOKEN = Token(0, GregorianCalendar.getInstance())
    }
}