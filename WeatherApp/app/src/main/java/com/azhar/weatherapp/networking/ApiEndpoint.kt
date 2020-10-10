package com.azhar.weatherapp.networking

/**
 * Created by Azhar Rivaldi on 03-10-2020
 */

object ApiEndpoint {
    var BASEURL = "http://api.openweathermap.org/data/2.5/"
    var CurrentWeather = "weather?"
    var ListWeather = "forecast?"
    var Daily = "forecast/daily?"
    var UnitsAppid = "&units=metric&appid=9ed2257682b1d9a2eb66c15047e1bfdd"
    var UnitsAppidDaily = "&units=metric&cnt=15&appid=9ed2257682b1d9a2eb66c15047e1bfdd"
}