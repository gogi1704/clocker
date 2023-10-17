package com.l_george.clocker.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.l_george.clocker.data.db.entities.toModel
import com.l_george.clocker.data.models.AlarmModel
import com.l_george.clocker.data.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    application: Application,
    private val repository: AlarmRepository
) : AndroidViewModel(application) {


    val alarmsLiveData = repository.getAll()
        .map { list ->
            list.map {
                it.toModel()
            }
        }
        .flowOn(Dispatchers.Default)
        .asLiveData()

    fun insertAlarm(alarmModel: AlarmModel) {
        viewModelScope.launch {
            repository.insertAlarm(alarmModel)
        }
    }

}