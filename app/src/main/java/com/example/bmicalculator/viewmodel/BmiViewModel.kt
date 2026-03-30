package com.example.bmicalculator.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bmicalculator.model.BmiData
import com.example.bmicalculator.model.buildBmiResult
import kotlinx.coroutines.flow.MutableStateFlow

class BmiViewModel: ViewModel() { // inherit husi class ViewModel atu iha ability par suvice screen roation
    var weight by mutableStateOf("")
        private set
    var height by mutableStateOf("")
        private set
    var result by mutableStateOf<BmiData?>(null)
    fun onWeightChange(value: String) { weight = value }
    fun onHeightChange(value: String) { height = value }

    fun calculate() {
        result = buildBmiResult(weight, height)
    }

    fun reset() {
        weight = ""
        height = ""
        result = null
    }

}