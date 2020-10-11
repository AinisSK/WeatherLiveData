package by.skabs.weather.network

import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class WeatherApi {

    private val client = OkHttpClient()

    fun getWeather(
        location: Pair<String, String>,
        onSuccess: (Int) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        "https://api.weather.yandex.ru/v2/informers?"
            .toHttpUrlOrNull()
            ?.newBuilder()
            ?.apply {
                addQueryParameter("lat", location.first)
                addQueryParameter("lon", location.second)
            }?.let {
                val request = Request.Builder()
                    .url(it.build())
                    .build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        onFailure(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val temp = response.body?.string()?.let {
                            JSONObject()
                                .getJSONObject("fact")
                                .getInt("temp")
                        } ?: Int.MIN_VALUE
                        onSuccess(temp)
                    }

                })
            }

    }
}