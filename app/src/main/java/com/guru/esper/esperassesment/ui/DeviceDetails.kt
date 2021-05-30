package com.guru.esper.esperassesment.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.guru.esper.esperassesment.R
import com.guru.esper.esperassesment.database.models.Features
import com.guru.esper.esperassesment.databinding.FragmentDeviceDetailsBinding
import com.guru.esper.esperassesment.viewmodel.MobileViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DeviceDetails : Fragment() {

    private val mainViewModel by sharedViewModel<MobileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dataBinding = DataBindingUtil.inflate<FragmentDeviceDetailsBinding>(
            inflater,
            R.layout.fragment_device_details,
            container,
            false
        )
        dataBinding.viewModel = mainViewModel
        return dataBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val feature1 = mainViewModel.feature1Summary.get()
        val image = view.findViewById<AppCompatImageView>(R.id.mobile_image)
        Glide.with(this).load(feature1?.optionIcon).into(image)
        mainViewModel.mobileName.set(feature1?.optionName)
        mainViewModel.storageName.set(mainViewModel.feature2Summary.get()?.optionName)
        setCategoryChips(mainViewModel.feature3Summary.get()!!)


    }


        private fun setCategoryChips(categorys: List<Features>) {
        val chipGroup = view?.findViewById<ChipGroup>(R.id.chipGroupSummary)
        for (category in categorys) {
            val mChip =
                this.layoutInflater.inflate(R.layout.item_chip_category, null, false) as Chip
            mChip.text = category.optionName
            mChip.id = category.optionID.toInt()
            mChip.height = 100
            mChip.setOnCheckedChangeListener { buttonView, isChecked ->
            }
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
        }
    }


}