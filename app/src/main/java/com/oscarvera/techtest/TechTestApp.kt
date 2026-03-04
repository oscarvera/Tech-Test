package com.oscarvera.techtest

import android.app.Application
import com.oscarvera.techtest.core.di.appModule
import com.oscarvera.techtest.core.di.databaseModule
import com.oscarvera.techtest.core.di.networkModule
import com.oscarvera.techtest.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TechTestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TechTestApp)
            modules(
                appModule,
                networkModule,
                databaseModule,
                viewModelModule
            )
        }

    }
}