package com.l_george.clocker.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.l_george.clocker.R
import com.l_george.clocker.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Date

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val format = SimpleDateFormat("HH:mm EEEE dd MMMM")
        val currentTime = format.format(Date())

        with(binding) {
            date.text = currentTime.subSequence(5 , currentTime.length)
            buttonSeeWorld.setOnClickListener {
                findNavController().navigate(R.id.worldFragment)
            }
            buttonSeeAlarm.setOnClickListener {
                findNavController().navigate(R.id.alarmFragment)
            }
        }

        val backCallBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallBack)




        requireActivity().findViewById<ImageView>(R.id.home).setImageResource(R.drawable.home_on)
        requireActivity().findViewById<ImageView>(R.id.world).setImageResource(R.drawable.world_off)
        requireActivity().findViewById<ImageView>(R.id.alarm).setImageResource(R.drawable.alarm_off)
        return binding.root
    }

}