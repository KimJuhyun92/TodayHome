package com.cloneproject.todayhome.data.repository

import com.cloneproject.todayhome.data.datasource.local.TodayHomeLocalSource
import com.cloneproject.todayhome.data.datasource.remote.TodayHomeRemoteSource
import javax.inject.Inject

class TodayHomeRepository @Inject constructor(
    private val todayHomeRemoteSource: TodayHomeRemoteSource,
    private val todayHomeLocalSource: TodayHomeLocalSource
){
}