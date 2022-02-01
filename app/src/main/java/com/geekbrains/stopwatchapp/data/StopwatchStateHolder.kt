package com.geekbrains.stopwatchapp.data

import com.geekbrains.stopwatchapp.StopwatchState
import com.geekbrains.stopwatchapp.TimestampMillisecondsFormatter
import com.geekbrains.stopwatchapp.data.calc.ElapsedTimeCalculator
import com.geekbrains.stopwatchapp.data.calc.StopwatchStateCalculator


class StopwatchStateHolder
    (private val stopwatchStateCalculator: StopwatchStateCalculator,
     private val elapsedTimeCalculator: ElapsedTimeCalculator,
     private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter
) {

    var currentState: StopwatchState = StopwatchState.Paused(0)
        private set

    fun start() {
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    fun pause() {
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }

    fun stop() {
        currentState = StopwatchState.Paused(0)
    }

    fun getStringTimeRepresentation(): String {
        val elapsedTime = when (val currentState = currentState) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running -> elapsedTimeCalculator.calculate(currentState)
        }
        return timestampMillisecondsFormatter.format(elapsedTime)
    }


}