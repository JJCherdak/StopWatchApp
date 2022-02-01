package com.geekbrains.stopwatchapp.data

interface TimestampProvider {
    fun getMilliseconds(): Long
}