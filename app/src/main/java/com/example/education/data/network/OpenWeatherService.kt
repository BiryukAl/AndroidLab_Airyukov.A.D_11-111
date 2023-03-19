package com.example.education.data.network

import android.os.Build
import com.example.education.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherService {

/*    private var okHttpClient: OkHttpClient? = null
    private var retrofitInstance: OpenWeatherApiService? = null*/

    // Ленивая инициализация через делегат
    private val okHttpClient: OkHttpClient = getOkHttpClient()

    private val retrofitInstance: OpenWeatherApiService by lazy {
        createRetrofitInstance()
    }


    private val okHttpClientLazy: OkHttpClient by lazy {
        getOkHttpClient()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val modifiedUrl = chain.request().url.newBuilder()
                    .addQueryParameter("appid", BuildConfig.WEATHER_KEY)
                    .addQueryParameter("units", "metric")
                    .build()
                val request = chain.request().newBuilder().url(modifiedUrl).build()
                chain.proceed(request)
            }

        return client.build()
    }


    private val retrofitInstanceLazy: OpenWeatherApiService by lazy {
        createRetrofitInstance()
    }

    private fun createRetrofitInstance(): OpenWeatherApiService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_BASE_URL)
            .client(okHttpClient?: OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitBuilder.create(OpenWeatherApiService::class.java)
    }

    /*private fun getRetrofitInstance() {
        if (okHttpClient == null) {
            okHttpClient = getOkHttpClient()
        }
        retrofitInstance = createRetrofitInstance()
    }*/

    fun getInstance(): OpenWeatherApiService = retrofitInstance

}
