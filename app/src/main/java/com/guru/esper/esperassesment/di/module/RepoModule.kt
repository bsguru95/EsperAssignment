package com.guru.esper.esperassesment.di.module

import android.content.Context
import com.guru.esper.esperassesment.database.MobileDaoInterface
import com.guru.esper.esperassesment.database.converter.MobileDataConverter
import com.guru.esper.esperassesment.database.converter.MobileDataInterface
import com.guru.esper.esperassesment.network.Api
import com.guru.esper.esperassesment.repository.MobileRepo
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val repositoryModule = module {

    fun provideMobileRepository(api: Api, dataConverter: MobileDataInterface, mobileDao: MobileDaoInterface, context: Context): MobileRepo {
        return MobileRepo(api, dataConverter, mobileDao)
    }
    single { provideMobileRepository(get(), get(), get(), androidContext()) }

    fun provideDataConverter(): MobileDataInterface {
        return MobileDataConverter()
    }
    single { provideDataConverter() }
}