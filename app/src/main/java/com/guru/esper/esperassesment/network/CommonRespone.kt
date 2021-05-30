package com.guru.esper.esperassesment.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CommonRespone {
    @SerializedName("features")
    @Expose
    var features: List<FeaturesApi>? = null

    @SerializedName("exclusions")
    @Expose
    var exclusions: List<List<ExclusionsApi>>? = null

    override fun toString(): String {
        return "ResponseApiModel(features= $features, exclusions= $exclusions)"
    }
}

class ExclusionsApi {

    @SerializedName("feature_id")
    @Expose
    var featureID: String? = null

    @SerializedName("options_id")
    @Expose
    var optionID: String? = null

    override fun toString(): String {
        return "Exclusion(featureID= $featureID, optionID= $optionID)"
    }
}

class FeaturesApi {

    @SerializedName("feature_id")
    @Expose
    var featureID: String? = null

    @SerializedName("name")
    @Expose
    var featureName: String? = null

    @SerializedName("options")
    @Expose
    var options: List<OptionsApi>? = null

    override fun toString(): String {
        return "Features(featureID= $featureID, featureName= $featureName, options= $options)"
    }
}

class OptionsApi {

    @SerializedName("name")
    @Expose
    var optionsName: String? = null

    @SerializedName("icon")
    @Expose
    var optionsIcon: String? = null

    @SerializedName("id")
    @Expose
    var optionsID: String? = null

    override fun toString(): String {
        return "Options(optionsName= $optionsName, optionsIcon= $optionsIcon, optionsID= $optionsID)"
    }
}