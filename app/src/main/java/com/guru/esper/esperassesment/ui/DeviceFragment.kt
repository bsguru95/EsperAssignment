package com.guru.esper.esperassesment.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.guru.esper.esperassesment.R
import com.guru.esper.esperassesment.database.models.Features
import com.guru.esper.esperassesment.databinding.FragmentDeviceBinding
import com.guru.esper.esperassesment.viewmodel.MobileViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class DeviceFragment : Fragment() {
    private val mainViewModel by sharedViewModel<MobileViewModel>()
    private var list: List<Features> = emptyList()
    private var selectedIds = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dataBinding = DataBindingUtil.inflate<FragmentDeviceBinding>(
            inflater,
            R.layout.fragment_device,
            container,
            false
        )
        dataBinding.viewModel = mainViewModel
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.featuresLiveData.observe(viewLifecycleOwner, Observer { featureDataList ->
            if (featureDataList.isNotEmpty() && featureDataList != null) {
                list = featureDataList
                featureDataList.forEach {
                    if (it.featureID.toInt() == 1)
                        mainViewModel.featureName1.set(it.featureName)
                    if (it.featureID.toInt() == 2)
                        mainViewModel.featureName2.set(it.featureName)
                    if (it.featureID.toInt() == 3)
                        mainViewModel.featureName3.set(it.featureName)
                }
                val f1 = featureDataList.filter {
                    it.featureID == "1"
                }
                setCategoryChips(f1)
                val f2 = featureDataList.filter {
                    it.featureID == "2"
                }
                setCategoryChips(f2)
                val f3 = featureDataList.filter {
                    it.featureID == "3"
                }
                setCategoryChips(f3)


            } else {
                Toast.makeText(
                    view.context,
                    getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        mainViewModel.exclusionsLiveDataForCheckedItems.observe(viewLifecycleOwner, Observer {
            Log.d("EXCLUSIONS LDATA check", it.toString())
            if (it.isNotEmpty()) {
                val l = ArrayList<Features>()
                it.forEach { optionId ->
                    list.forEach { featureData ->
                        if (featureData.optionID == optionId)
                            l.add(featureData)
                    }
                }
                disableExclusionsForCheckedItem(l)
            }
        })

        mainViewModel.exclusionsLiveDataForUnCheckedItems.observe(viewLifecycleOwner, Observer {
            Log.d("EXCLUSIONS LDATA uncheck", it.toString())
            if (it.isNotEmpty()) {
                val l = ArrayList<Features>()
                it.forEach { optionId ->
                    list.forEach { featureData ->
                        if (featureData.optionID == optionId)
                            l.add(featureData)
                    }
                }
                enableExclusionsForUnCheckedItem(l)
            }
        })

        mainViewModel.submitClicked.observe(viewLifecycleOwner, Observer {
            if (it) {
                val featureList1 = generateSummary(view.findViewById(R.id.chipGroupMobiles))
                if (featureList1.isNotEmpty())
                    mainViewModel.feature1Summary.set(generateSummary(view.findViewById(R.id.chipGroupMobiles))[0])

                val featureList2 = generateSummary(view.findViewById(R.id.chipGroupStorage))
                if (featureList2.isNotEmpty())
                    mainViewModel.feature2Summary.set(generateSummary(view.findViewById(R.id.chipGroupStorage))[0])

                val featureList3 = generateSummary(view.findViewById(R.id.chipGroupFeatures))
                if (featureList3.isNotEmpty())
                    mainViewModel.feature3Summary.set(generateSummary(view.findViewById(R.id.chipGroupFeatures)))
                if (featureList1.isEmpty() || featureList2.isEmpty() || featureList3.isEmpty())
                    Toast.makeText(
                        view.context,
                        getString(R.string.warning_to_make_selection),
                        Toast.LENGTH_LONG
                    ).show()
                else
                    Navigation.findNavController(view)
                        .navigate(R.id.action_navigation_mobile_list_to_navigation_summary)
            }
        })
    }

    //function to get summary details to show in the next screen
    private fun generateSummary(chipGroup: ChipGroup): List<Features> {
        val ids = chipGroup.children.filter { it as Chip
            it.isChecked && it.isCheckable
        }
        val featureDataSummaryList = ArrayList<Features>()
        list.forEach { featureData ->
            ids.forEach {
                if (featureData.optionID.toInt() == it.id)
                    featureDataSummaryList.add(featureData)
            }
        }
        return featureDataSummaryList
    }

    //function to create dynamic chips and set to correponding chipgroup declared in the UI
    private fun setCategoryChips(
        categories: List<Features>,
        enabled: Boolean = true,
        checked: Boolean = false
    ) {
        for (category in categories) {
            val chipGroup = getChipGroup(category)
            val mChip =
                this.layoutInflater.inflate(R.layout.item_chip_category, null, false) as Chip
            mChip.text = category.optionName
            mChip.id = category.optionID.toInt()
            mChip.height = 100
            mChip.setOnCheckedChangeListener { buttonView, isChecked ->
                Log.d("TAG button isChecked ${buttonView.id}", "${isChecked.toString()} -- featureID- ${category.featureID}")
                if(isChecked)
                    getExclusions(category.featureID, buttonView, checked = true)
                else
                    getExclusions(category.featureID, buttonView, checked= false)
            }
            if (enabled) {
                Glide.with(this)
                    .load(category.optionIcon)
                    .into(object : CustomTarget<Drawable>(48, 48) {
                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            mChip.chipIcon = errorDrawable
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            mChip.chipIcon = resource
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            mChip.chipIcon = placeholder
                        }

                    })
                chipGroup?.addView(mChip)
            } else {
                Log.d("EXCLUSIONS TAB category", category.toString())
                val chipGroupToDisable = getChipGroup(category)
                chipGroupToDisable?.setChildrenEnabled(category.optionID.toInt(), checked)
            }
        }
    }

    private fun disableExclusionsForCheckedItem(features: List<Features>) {

        features.forEach {  featureData ->
            val chipGroupToDisable = getChipGroup(featureData)
            chipGroupToDisable?.setChildrenEnabled(featureData.optionID.toInt(), false)
        }
    }

    private fun enableExclusionsForUnCheckedItem(features: List<Features>) {

        features.forEach {  featureData ->
            val chipGroupToDisable = getChipGroup(featureData)
            chipGroupToDisable?.setChildrenEnabled(featureData.optionID.toInt(), true)
        }
    }

    private fun getChipGroup(category: Features) : ChipGroup? {
        return when {
            category.featureID.toInt() == 1 -> {
                view?.findViewById(R.id.chipGroupMobiles)
            }
            category.featureID.toInt() == 2 -> {
                view?.findViewById(R.id.chipGroupStorage)
            }
            category.featureID.toInt() == 3 -> {
                view?.findViewById(R.id.chipGroupFeatures)
            }
            else -> {
                view?.findViewById(R.id.chipGroupMobiles)
            }
        }
    }

    //extension function to set disabled status for the chips already present in-case combination is not valid
    private fun ChipGroup.setChildrenEnabled(id: Int, enable : Boolean) {
        children.forEach { it as Chip
            if (it.id == id) {
                if (it.isChecked) {
                    it.isChecked = false
                    it.isCheckable = false
                }
                it.isEnabled = enable
            }
        }
    }

    //function to get exclusions according to user inputs, checked value will change if chips is selected/deselected
    private fun getExclusions(featureID: String, buttonView: CompoundButton?, checked: Boolean) {
        if (checked)
            mainViewModel.getExclusions(
                featureID = featureID,
                optionsID = buttonView?.id.toString(),
                checked = true
            )
        else
            mainViewModel.getExclusions(
                featureID = featureID,
                optionsID = buttonView?.id.toString(),
                checked = false
            )
    }
}