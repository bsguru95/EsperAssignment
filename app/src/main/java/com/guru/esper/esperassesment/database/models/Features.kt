package com.guru.esper.esperassesment.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Features")
data class Features(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "featureID")
    val featureID : String,
    @ColumnInfo(name = "featureName")
    val featureName : String,
    @ColumnInfo(name = "optionID")
    val optionID : String,
    @ColumnInfo(name = "optionName")
    val optionName : String,
    @ColumnInfo(name = "optionIcon")
    val optionIcon : String,
)
