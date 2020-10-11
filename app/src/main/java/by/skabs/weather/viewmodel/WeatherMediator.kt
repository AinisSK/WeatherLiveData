package by.skabs.weather.viewmodel

import androidx.lifecycle.MediatorLiveData

class WeatherMediator(private val location: Array<Pair<String, String>>) :
    MediatorLiveData<Pair<Pair<String, String>, Int>>() {

    private val results = HashMap<Pair<String, String>, Int>()

    init {
        location.forEach { location ->
            val liveData = WeatherLiveData(location)
            addSource(liveData) { obtainResult(location, it) }
        }
    }

    private fun obtainResult(loc: Pair<String, String>, temp: Int) {
        results[loc] = temp
        if (results.size == location.size) {
            value = results.maxBy { it.value }?.toPair()
        }
    }
}