package com.geekbrains.stopwatchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geekbrains.cleancodeapp.R
import com.geekbrains.cleancodeapp.databinding.ActivityMainBinding
import com.geekbrains.stopwatchapp.data.StopwatchStateHolder
import com.geekbrains.stopwatchapp.data.TimestampProvider
import com.geekbrains.stopwatchapp.data.TimestampProviderImpl
import com.geekbrains.stopwatchapp.data.calc.ElapsedTimeCalculator
import com.geekbrains.stopwatchapp.data.calc.StopwatchStateCalculator
import com.geekbrains.stopwatchapp.viewmodel.StopwatchViewModel
import com.geekbrains.stopwatchapp.viewmodel.StopwatchViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val timestampProvider: TimestampProvider = TimestampProviderImpl()


    private lateinit var modelFirst: StopwatchViewModel
    private lateinit var modelSecond: StopwatchViewModel

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        scope.launch {
            modelFirst.ticker.collect{
                binding.textTimeOne.text = it
            }
        }

        scope.launch {
            modelFirst.ticker.collect{
                binding.textTimeTwo.text = it
            }
        }
    }


    private fun init() {
        modelFirst = StopwatchViewModelFactory(
            stopwatchStateHolder = StopwatchStateHolder(
                StopwatchStateCalculator(
                    timestampProvider,
                    ElapsedTimeCalculator(timestampProvider),
                ),
                ElapsedTimeCalculator(timestampProvider),
                TimestampMillisecondsFormatter()
            ),
            CoroutineScope(
                Dispatchers.Main
                        + SupervisorJob()
            )
        ).create(StopwatchViewModel::class.java)

        modelSecond = StopwatchViewModelFactory(
            stopwatchStateHolder = StopwatchStateHolder(
                StopwatchStateCalculator(
                    timestampProvider,
                    ElapsedTimeCalculator(timestampProvider),
                ),
                ElapsedTimeCalculator(timestampProvider),
                TimestampMillisecondsFormatter()
            ),
            CoroutineScope(
                Dispatchers.Main
                        + SupervisorJob()
            )
        ).create(StopwatchViewModel::class.java)

        binding.buttonStartOne.setOnClickListener {
            modelFirst.start()
        }
        binding.buttonPauseOne.setOnClickListener {
            modelFirst.pause()
        }
        binding.buttonStopOne.setOnClickListener {
            modelFirst.stop()
        }
        binding.buttonStartTwo.setOnClickListener {
            modelSecond.start()
        }
        binding.buttonPauseTwo.setOnClickListener {
            modelSecond.pause()
        }
        binding.buttonStopTwo.setOnClickListener {
            modelSecond.stop()
        }
    }
}



