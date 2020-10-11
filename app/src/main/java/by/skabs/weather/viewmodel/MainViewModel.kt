package by.skabs.weather.viewmodel

import androidx.lifecycle.*

class MainViewModel : ViewModel() {

    private val weatherLiveData: LiveData<Pair<Pair<String, String>, Int>>
    val locationLiveData = MutableLiveData<Array<Pair<String, String>>>()

    init {
        weatherLiveData =
            Transformations.switchMap(locationLiveData) { input: Array<Pair<String, String>>? ->
                input?.let {
                    WeatherMediator(it)
                }
            }
    }
}