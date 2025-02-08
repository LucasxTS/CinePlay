package com.example.teste.domain.network.authIntercepetor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmM2Y2FjZGQ1YzdlODNmOWM3MWI0MWUxOWM0N2JlMiIsIm5iZiI6MTczOTAyMDI1Mi42NzUsInN1YiI6IjY3YTc1N2RjNWZhNDJkN2U3NmYxMTFkNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.csaN0LmJ_FlharMLBewvJmos2ggCdHnvIRbg3Kn93bw'")
            .build()
        return chain.proceed(request)
    }
}