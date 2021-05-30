package com.guru.esper.esperassesment.database.converter

import com.guru.esper.esperassesment.database.models.Exclusions
import com.guru.esper.esperassesment.database.models.Features
import com.guru.esper.esperassesment.network.CommonRespone

interface MobileDataInterface {
    fun apiToFeatures(api: CommonRespone): List<Features>

    fun apiToExclusions(api: CommonRespone): List<Exclusions>
}