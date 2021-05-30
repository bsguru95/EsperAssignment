package com.guru.esper.esperassesment.viewmodel

import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guru.esper.esperassesment.addToComposite
import com.guru.esper.esperassesment.database.models.Features
import com.guru.esper.esperassesment.repository.MobileRepo
import io.reactivex.disposables.CompositeDisposable

class MobileViewModel(    private val repository: MobileRepo
):ViewModel() {

    val featureName1 = ObservableField<String>("")
    val featureName2 = ObservableField<String>("")
    val featureName3 = ObservableField<String>("")
    val showLoading = ObservableBoolean()

    val featuresLiveData = MutableLiveData<List<Features>>()
    val exclusionsLiveDataForCheckedItems = MutableLiveData< List<String>>()
    val exclusionsLiveDataForUnCheckedItems = MutableLiveData<List<String>>()
    val submitClicked = MutableLiveData<Boolean>()

    val feature1Summary = ObservableField<Features>()
    val feature2Summary = ObservableField<Features>()
    val feature3Summary = ObservableField<List<Features>>()

    val storageName = ObservableField<String>()
    val mobileName = ObservableField<String>()
    private val disposables = CompositeDisposable()

    fun fetchDataFromAPI() {
        showLoading.set(true)
        repository.fetchDataFromAPI()
            .subscribe({
                showLoading.set(false)
                Log.d("MainViewModel", "fetchDataFromAPI(): Success")
                fetchFeaturesDataFromDB()
            },
                {
                    fetchFeaturesDataFromDB()
                })
            .addToComposite(disposables)
    }

    fun fetchFeaturesDataFromDB() {
        showLoading.set(true)
        repository.getFeaturesData()
            .subscribe({
                showLoading.set(false)
                featuresLiveData.postValue(it)
            },
                {
                    Log.d("MainViewModel", it.toString())
                }
            )
            .addToComposite(disposables)
    }

    fun getExclusions(featureID: String, optionsID: String, checked: Boolean) {
        val result = repository.getExclusions(featureID, optionsID)
        val list = ArrayList<String>()
        result.forEach {
            if (it.optionID1 == optionsID)
                list.add(it.optionID2)
            if (it.optionID2 == optionsID)
                list.add(it.optionID1)
        }
        if(checked)
            exclusionsLiveDataForCheckedItems.postValue(list)
        else
            exclusionsLiveDataForUnCheckedItems.postValue(list)
    }

    fun onClickSubmit(view: View) {
        submitClicked.postValue(true)
    }
}