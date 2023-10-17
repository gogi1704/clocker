package com.l_george.clocker.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.l_george.clocker.R
import com.l_george.clocker.data.models.WorldTimeModel
import com.l_george.clocker.databinding.FragmentWorldBinding
import com.l_george.clocker.ui.adapters.WorldAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class WorldFragment : Fragment() {
    private lateinit var binding: FragmentWorldBinding
    private lateinit var adapter: WorldAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorldBinding.inflate(layoutInflater, container, false)
        adapter = WorldAdapter()
        val format = SimpleDateFormat("HH:mm")
        val capitals = listOf(
            "Europe/Moscow", // Москва
            "America/New_York", // Нью-Йорк
            "Europe/London", // Лондон
            "Asia/Tokyo", // Токио
            "Australia/Sydney", // Сидней
            "Asia/Shanghai", // Шанхай
            "America/Los_Angeles", // Лос-Анджелес
            "Africa/Cairo", // Каир
            "Asia/Kolkata", // Калькутта
            "America/Sao_Paulo" // Сан-Паулу
        )
        val times = capitals.map { capital ->
            format.timeZone = TimeZone.getTimeZone(capital)
            val time = format.format(Date())
            WorldTimeModel(time.toString(), capital)

        }

        with(binding){
            recycler.adapter = adapter
            adapter.submitList(times.sortedBy { it.time })
        }






        requireActivity().findViewById<ImageView>(R.id.home).setImageResource(R.drawable.home_off)
        requireActivity().findViewById<ImageView>(R.id.world).setImageResource(R.drawable.world_on)
        requireActivity().findViewById<ImageView>(R.id.alarm).setImageResource(R.drawable.alarm_off)
        return binding.root
    }


}