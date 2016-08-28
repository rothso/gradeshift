package io.gradeshift.data.network.provider.focus.converter

import com.franmontiel.persistentcookiejar.cache.CookieCache
import com.github.ajalt.timberkt.d
import io.gradeshift.data.network.api.LoginApi
import io.gradeshift.data.network.auth.Token
import io.gradeshift.data.network.auth.User
import io.gradeshift.domain.model.Credentials
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.threeten.bp.Instant
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import rx.Observable
import rx.schedulers.Schedulers
import java.net.SocketTimeoutException

class FocusLoginApi(retrofit: Retrofit, private val cookieCache: CookieCache) : LoginApi {
    private val api: Api = retrofit.create(Api::class.java)

    private interface Api {

        @HEAD("./")
        fun initialize(): Observable<Response<Void>>

        @POST @FormUrlEncoded
        fun login(
                @Url loginUrl: String,
                @Field("UserName") username: String,
                @Field("Password") password: String
        ): Observable<Response<ResponseBody>>

        @POST("simplesaml/module.php/saml/sp/saml2-acs.php/default-sp") @FormUrlEncoded
        fun finalize(@Field("SAMLResponse") responseToken: String): Observable<Response<ResponseBody>>
    }

    companion object {

        private val getLoginUrl: (Response<*>) -> String = {
            it.raw().request().url().toString() + "&RedirectToIdentityProvider=urn%3Afederation%3ADCPS"
        }

        private val isLoginSuccessful: (Response<*>) -> Boolean = {
            it.headers().values("Set-Cookie").any { cookie -> cookie.startsWith("SamlSession=") }
        }

        private val getResponseToken: (Response<ResponseBody>) -> String = {
            Jsoup.parse(it.body().string())
                    .getElementsByAttributeValue("name", "SAMLResponse")[0]
                    .attr("value")
        }
    }

    override fun login(credential: Credentials): Observable<User?> {
        val username = credential.username
        val password = credential.password

        return api.initialize()
                .doOnNext { d { "Remotely logging in $username" } }
                .map(getLoginUrl)
                .flatMap { url -> api.login(url, "DCPS\\" + username, password) }
                .filter(isLoginSuccessful)
                .doOnNext { d { "Remote login successful" } }
                .map(getResponseToken)
                .flatMap { token -> api.finalize(token) }
                .retry { attempt, e -> e is SocketTimeoutException && attempt < 3 }
                .map f@ {
                    val value = cookieCache.find { it.name().startsWith("PHPSESSID") }?.value() ?: return@f null
                    val expiry = cookieCache.find { it.name().startsWith("session_timeout") }?.value() ?: return@f null
                    User(0, Token(value, Instant.ofEpochSecond(expiry.toLong())))
                }
                .defaultIfEmpty(null)
                .subscribeOn(Schedulers.io())
    }
}