package com.example.teste

import android.app.Application
import com.example.teste.commons.di.apiModule
import com.example.teste.commons.di.dataBaseModule
import com.example.teste.commons.di.repositoryModule
import com.example.teste.commons.di.useCasesModule
import com.example.teste.commons.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication(): Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppApplication)
            modules(
                listOf(
                    apiModule,
                    repositoryModule,
                    viewModelModule,
                    dataBaseModule,
                    useCasesModule
                )
            )
        }
    }
}