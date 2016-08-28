package io.gradeshift.data.network

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.CookieCache
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor
import okhttp3.Cookie

/* PersistentCookieJar - Persistence + Proguard amounts to exactly what would otherwise be written. */
class CacheCookieJar(cache: CookieCache) : PersistentCookieJar(cache, NoopCookiePersistor()) {
    private class NoopCookiePersistor : CookiePersistor {
        private val noop = Unit

        override fun saveAll(cookies: MutableCollection<Cookie>?) = noop
        override fun removeAll(cookies: MutableCollection<Cookie>?) = noop
        override fun loadAll(): MutableList<Cookie>? = mutableListOf()
        override fun clear() = noop
    }
}