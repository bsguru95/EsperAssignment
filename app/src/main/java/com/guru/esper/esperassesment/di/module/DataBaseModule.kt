package com.guru.esper.esperassesment.di.module

import android.app.Application
import androidx.room.Room
import com.guru.esper.esperassesment.database.MobileDaoInterface
import com.guru.esper.esperassesment.database.MobileDb
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataBaseModule = module {

    fun provideDatabase(application: Application): MobileDb {
        return Room.databaseBuilder(application, MobileDb::class.java, "mobiles")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideMobileDao(database: MobileDb): MobileDaoInterface {
        return  database.mobileDaoInterface
    }

    single { provideDatabase(androidApplication()) }
    single { provideMobileDao(get()) }

}