package com.guru.esper.esperassesment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guru.esper.esperassesment.database.models.Exclusions
import com.guru.esper.esperassesment.database.models.Features
import io.reactivex.Flowable
@Dao
interface MobileDaoInterface {
    //query to get all the features to show in the UI
    @Query("SELECT * FROM Features")
    fun findAll(): Flowable<List<Features>>

    //adding featureModel after conversion from api response to table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun addFeatures(features: List<Features>)

    //adding exclusionsModel after conversion from api response to table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun addExclusions(exclusions: List<Exclusions>)

    //helps to delete all the rows from features table before adding new set of features in table
    @Query("DELETE FROM Features")
    fun deleteFeatures()

    //helps to delete all the rows from exclusions table before adding new set of exclusions in table
    @Query("DELETE FROM Exclusions")
    fun deleteExclusions()

    //query to find the exclusion by matching the user input
    @Query("SELECT * FROM Exclusions WHERE (featureID1 == :featureID OR featureID2 == :featureID) AND (optionID1 == :optionsID OR optionID12 == :optionsID)")
    fun getExclusion(featureID: String, optionsID: String): List<Exclusions>

}