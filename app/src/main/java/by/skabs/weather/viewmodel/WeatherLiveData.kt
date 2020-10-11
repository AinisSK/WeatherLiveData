package by.skabs.weather.viewmodel

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import by.skabs.weather.network.WeatherApi

class WeatherLiveData(private val location: Pair<String, String>) : LiveData<Int>() {

    private val api = WeatherApi()
    private val handler = Handler()

    private val requestTask = Runnable {
        api.getWeather(location, this::onSuccess) {
            Log.e("Error", it.message ?: "")
        }
    }

    override fun onActive() {
        super.onActive()
        handler.post(requestTask)
    }

    override fun onInactive() {
        super.onInactive()
        handler.removeCallbacks(requestTask)
    }

    private fun onSuccess(temp: Int) {
        Log.i("Result", temp.toString())
        value = temp
        handler.postDelayed(requestTask, 5000)
    }
}