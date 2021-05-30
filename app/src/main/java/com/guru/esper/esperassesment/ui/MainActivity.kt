package com.guru.esper.esperassesment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.guru.esper.esperassesment.EsperApplication
import com.guru.esper.esperassesment.R
import com.guru.esper.esperassesment.viewmodel.MobileViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel : MobileViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_mobile_list,
            R.id.navigation_summary
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        //checks for internet connectivity before initiating n/w call
        if(EsperApplication.isOnline(this))
            mainViewModel.fetchDataFromAPI()
        else {
            mainViewModel.fetchFeaturesDataFromDB()
            Toast.makeText(this, R.string.no_network_connectivity, Toast.LENGTH_LONG).show()
        }

    }

}