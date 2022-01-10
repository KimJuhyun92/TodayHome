package com.cloneproject.todayhome.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.cloneproject.todayhome.data.repository.TodayHomeRepository

class HomeViewModel @ViewModelInject constructor(
    private val todayHomeRepository: TodayHomeRepository
) : ViewModel() {

}