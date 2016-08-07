package io.gradeshift.data.network.auth

import android.content.SharedPreferences
import io.gradeshift.data.util.edit
import org.threeten.bp.Instant
import rx.Observable
import rx.Statement

class SessionStore(@UserScope val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_TOKEN_VALUE = "auth.token.value"
        private const val KEY_TOKEN_EXPIRY = "auth.token.expiry"
    }

    fun getToken(): Observable<Token> {
        return Observable.defer {
            val value: String = sharedPreferences.getString(KEY_TOKEN_VALUE, "")
            val expiry: Long = sharedPreferences.getLong(KEY_TOKEN_EXPIRY, -1)

            val token = Observable.just(Token(value, Instant.ofEpochSecond(expiry)))
            Statement.ifThen({ value.isBlank() || expiry > 0 }, token)
        }
    }

    fun saveToken(token: Token) {
        sharedPreferences.edit {
            putString(KEY_TOKEN_VALUE, token.value)
            putLong(KEY_TOKEN_EXPIRY, token.expiry.epochSecond)
        }
    }
}