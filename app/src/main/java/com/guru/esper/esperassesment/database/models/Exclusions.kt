package com.guru.esper.esperassesment.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Exclusions")
data class Exclusions(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "featureID1")
    val featureID1: String,
    @ColumnInfo(name = "optionID1")
    val optionID1: String,
    @ColumnInfo(name = "featureID2")
    val featureID2: String,
    @ColumnInfo(name = "optionID12")
    val optionID2: String
)
