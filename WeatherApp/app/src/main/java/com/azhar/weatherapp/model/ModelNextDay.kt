package com.azhar.weatherapp.model

import java.io.Serializable

/**
 * Created by Azhar Rivaldi on 07-10-2020
 */

class ModelNextDay : Serializable {
    var nameDay: String? = null
    var nameDate: String? = null
    var descWeather: String? = null
    var tempMax = 0.0
    var tempMin = 0.0
}