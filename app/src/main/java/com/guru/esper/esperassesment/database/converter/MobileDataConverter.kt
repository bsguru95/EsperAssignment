package com.guru.esper.esperassesment.database.converter

import com.guru.esper.esperassesment.database.models.Exclusions
import com.guru.esper.esperassesment.database.models.Features
import com.guru.esper.esperassesment.network.CommonRespone
import java.util.*
import kotlin.collections.ArrayList

class MobileDataConverter :MobileDataInterface{
    override fun apiToFeatures(api: CommonRespone): List<Features> {
        val featuresDataList = ArrayList<Features>()
        api.features?.forEach { featureApi ->
            featureApi.options?.forEach { optionsApi ->
                val featureData = Features(
                    id = UUID.randomUUID().toString(),
                    featureID = featureApi.featureID ?: "",
                    featureName = featureApi.featureName ?: "",
                    optionID = optionsApi.optionsID ?: "",
                    optionIcon = optionsApi.optionsIcon ?: "",
                    optionName = optionsApi.optionsName ?: ""
                )
                featuresDataList.add(featureData)
            }
        }
        return featuresDataList
    }

    override fun apiToExclusions(api: CommonRespone): List<Exclusions> {
        val exclusionsList = ArrayList<Exclusions>()
        api.exclusions?.forEach {
            val exclusionData = Exclusions(
                id = UUID.randomUUID().toString(),
                featureID1 = it[0].featureID ?: "",
                featureID2 = it[1].featureID ?: "",
                optionID1 = it[0].optionID ?: "",
                optionID2 = it[1].optionID ?: ""
            )
            exclusionsList.add(exclusionData)
        }
        return exclusionsList
    }
}