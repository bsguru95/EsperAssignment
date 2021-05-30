package com.guru.esper.esperassesment.di.module

import com.guru.esper.esperassesment.viewmodel.MobileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build MainViewModel
    viewModel {
        MobileViewModel(repository = get())
    }

}