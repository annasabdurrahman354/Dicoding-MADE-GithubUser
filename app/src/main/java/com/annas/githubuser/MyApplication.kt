package com.annas.githubuser

import android.app.Application
import com.annas.githubuser.core.di.databaseModule
import com.annas.githubuser.core.di.networkModule
import com.annas.githubuser.core.di.repositoryModule
import com.annas.githubuser.di.useCaseModule
import com.annas.githubuser.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}