package com.geekbrains.stopwatchapp.data.calc

import com.geekbrains.stopwatchapp.StopwatchState
import com.geekbrains.stopwatchapp.data.TimestampProvider

class ElapsedTimeCalculator (private val timestampProvider: TimestampProvider) {
    fun calculate(state: StopwatchState.Running): Long {
        val currentTimestamp = timestampProvider.getMilliseconds()
        val timePassedSinceStart = if (currentTimestamp > state.startTime) {
            currentTimestamp - state.startTime
        } else {
            0
        }
        return timePassedSinceStart + state.elapsedTime
    }

}