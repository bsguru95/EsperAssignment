package com.guru.esper.esperassesment.repository

import com.guru.esper.esperassesment.database.models.Exclusions
import com.guru.esper.esperassesment.database.models.Features
import io.reactivex.Completable
import io.reactivex.Flowable

interface MobileListRepo {

    fun fetchDataFromAPI() : Completable

    fun getFeaturesData() : Flowable<List<Features>>

    fun getExclusions(featureID: String, optionsID: String) : List<Exclusions>
}