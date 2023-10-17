package com.l_george.clocker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.l_george.clocker.R
import com.l_george.clocker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentManager =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = fragmentManager.navController

        with(binding) {
            home.setOnClickListener {
                navController.navigate(R.id.homeFragment)
            }
            world.setOnClickListener {
                navController.navigate(R.id.worldFragment)

            }
            alarm.setOnClickListener {
                navController.navigate(R.id.alarmFragment)

            }
        }


    }
}