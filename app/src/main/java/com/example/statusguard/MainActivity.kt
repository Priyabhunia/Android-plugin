package com.example.statusguard

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.btnStartTime)
        val endButton = findViewById<Button>(R.id.btnEndTime)
        val enableServiceButton = findViewById<Button>(R.id.btnOpenAccessibility)
        val saveButton = findViewById<Button>(R.id.btnSave)
        val statusText = findViewById<TextView>(R.id.txtSavedWindow)

        var startTime = PreferencesManager.getStartTime(this)
        var endTime = PreferencesManager.getEndTime(this)

        updateTimeButtons(startButton, endButton, startTime, endTime)
        updateSummary(statusText, startTime, endTime)

        startButton.setOnClickListener {
            pickTime(startTime) {
                startTime = it
                updateTimeButtons(startButton, endButton, startTime, endTime)
            }
        }

        endButton.setOnClickListener {
            pickTime(endTime) {
                endTime = it
                updateTimeButtons(startButton, endButton, startTime, endTime)
            }
        }

        saveButton.setOnClickListener {
            PreferencesManager.saveTimes(this, startTime, endTime)
            updateSummary(statusText, startTime, endTime)
        }

        enableServiceButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
    }

    private fun updateTimeButtons(
        startButton: Button,
        endButton: Button,
        start: LocalTime,
        end: LocalTime
    ) {
        startButton.text = getString(R.string.block_start_time, start.format(formatter))
        endButton.text = getString(R.string.block_end_time, end.format(formatter))
    }

    private fun updateSummary(statusText: TextView, start: LocalTime, end: LocalTime) {
        statusText.text = getString(
            R.string.current_window,
            start.format(formatter),
            end.format(formatter)
        )
    }

    private fun pickTime(initial: LocalTime, onPicked: (LocalTime) -> Unit) {
        TimePickerDialog(
            this,
            { _, hour, minute ->
                onPicked(LocalTime.of(hour, minute))
            },
            initial.hour,
            initial.minute,
            true
        ).show()
    }
}
