package com.example.tvseries.data

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        sessionManager.getToken()?.let {
            requestBuilder.addHeader("access-token", it)
        }
        sessionManager.getClient()?.let {
            requestBuilder.addHeader("client", it)
        }
        sessionManager.getUid()?.let {
            requestBuilder.addHeader("uid", it)
        }
        return chain.proceed(requestBuilder.build())
    }
}