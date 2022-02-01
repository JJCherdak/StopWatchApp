package com.geekbrains.stopwatchapp.data

class TimestampProviderImpl: TimestampProvider {
    override fun getMilliseconds(): Long = System.currentTimeMillis()
}