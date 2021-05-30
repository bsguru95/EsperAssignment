package com.guru.esper.esperassesment.di.module

import com.guru.esper.esperassesment.network.Api
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    fun getMobileApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
    single { getMobileApi(get()) }

}