package com.azhar.weatherapp.model

import java.io.Serializable

/**
 * Created by Azhar Rivaldi on 03-10-2020
 */

class ModelMain : Serializable {
    var timeNow: String? = null
    var descWeather: String? = null
    var currentTemp = 0.0
    var tempMax = 0.0
    var tempMin = 0.0
}