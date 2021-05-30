package com.guru.esper.esperassesment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.guru.esper.esperassesment.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class EsperApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    //koin for dependency injection
    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidLogger()
            androidContext(this@EsperApplication)
            modules(
                requestModule,
                viewModelModule,
                repositoryModule,
                networkModule,
                dataBaseModule
            )
        }
    }

    companion object {

        //to check the network connection status
        fun isOnline(ctx: Context): Boolean {
            try {
                val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as? ConnectivityManager ?: return false
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
            return false
        }
    }
}