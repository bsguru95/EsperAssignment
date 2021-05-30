package com.guru.esper.esperassesment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guru.esper.esperassesment.database.models.Exclusions
import com.guru.esper.esperassesment.database.models.Features

@Database(
    entities = [Features::class, Exclusions::class],
    version = 1, exportSchema = false
)
abstract class MobileDb : RoomDatabase() {
    abstract val mobileDaoInterface: MobileDaoInterface
}