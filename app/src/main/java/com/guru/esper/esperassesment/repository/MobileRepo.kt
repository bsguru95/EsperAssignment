package com.guru.esper.esperassesment.repository

import com.guru.esper.esperassesment.database.MobileDaoInterface
import com.guru.esper.esperassesment.database.converter.MobileDataConverter
import com.guru.esper.esperassesment.database.converter.MobileDataInterface
import com.guru.esper.esperassesment.database.models.Exclusions
import com.guru.esper.esperassesment.database.models.Features
import com.guru.esper.esperassesment.network.Api
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class MobileRepo(private val mobileService: Api,
                 private val dataConverter: MobileDataInterface,
                 private val mobileDao: MobileDaoInterface):MobileListRepo {
    override fun fetchDataFromAPI(): Completable {
        return mobileService.getMobileData()
            .subscribeOn(Schedulers.io())
            .firstOrError()
            .flatMapCompletable { response ->
                if (response.isSuccessful) {
                    response.body()?.let { responseApi ->
                        val featuresDataList = dataConverter.apiToFeatures(responseApi)
                        val exclusionsDataList = dataConverter.apiToExclusions(responseApi)
                        mobileDao.deleteFeatures()
                        mobileDao.deleteExclusions()
                        mobileDao.addFeatures(featuresDataList)
                        mobileDao.addExclusions(exclusionsDataList)
                    }
                }
                Completable.complete()
            }
    }

    override fun getFeaturesData(): Flowable<List<Features>> {
        return mobileDao.findAll()
    }

    override fun getExclusions(featureID: String, optionsID: String): List<Exclusions> {
        return mobileDao.getExclusion(featureID, optionsID)
    }
}