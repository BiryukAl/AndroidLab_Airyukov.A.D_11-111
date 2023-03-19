package com.example.education.presentation.screen_new.homework12

import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.education.di.ViewModelArgsKeys
import com.example.education.domain.interactor.WeatherGetByCityInteractor
import com.example.education.domain.model.WeatherEntity
import com.example.education.domain.usecase.WeatherByCoordinateUseCase
import com.example.education.extansions.default
import com.example.education.extansions.set
import kotlinx.coroutines.async

sealed class WeatherState {
    class DefaultState : WeatherState()
    class LoadingState : WeatherState()
    class LoadedState(val data: WeatherEntity) : WeatherState()
    class ErrorState(val message: String) : WeatherState()
}

@Suppress("UNCHECKED_CAST")
class MainWeatherViewModel(
    private val weatherGetByCityInteractor: WeatherGetByCityInteractor,
    private val weatherByCoordinateUseCase: WeatherByCoordinateUseCase,
) : ViewModel() {
    val state = MutableLiveData<WeatherState>().default(initialValue = WeatherState.DefaultState())

    fun getWeatherByCoordinate(lat: Double, lon: Double) {
        state.set(WeatherState.LoadingState())

        viewModelScope.async {
            val data = weatherByCoordinateUseCase(lat, lon)
            if (data != null) {
                state.set(WeatherState.LoadedState(data))
            } else {
                state.set(WeatherState.ErrorState("Error: weather API"))
            }
        }
    }

    fun getWeatherByCity(cityName: String) {
        state.set(WeatherState.LoadingState())

        if (cityName.isBlank() && cityName.length < 2) {
            state.set(WeatherState.ErrorState("Error: empty input City"))
            return
        }

        viewModelScope.async {
            val data = weatherGetByCityInteractor(cityName, SystemClock.uptimeMillis())

            if (data != null) {
                state.set(WeatherState.LoadedState(data))
            } else {
                state.set(WeatherState.ErrorState("Error: weather API or DB"))
            }
        }
    }


    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val weatherGetByCityInteractor =
                    extras[ViewModelArgsKeys.weatherGetByCityInteractor]
                        ?: throw IllegalArgumentException()

                val weatherByCoordinateUseCase =
                    extras[ViewModelArgsKeys.weatherByCoordinateUseCase]
                        ?: throw IllegalArgumentException()

                return (MainWeatherViewModel(weatherGetByCityInteractor,
                    weatherByCoordinateUseCase) as? T) ?: throw java.lang.IllegalStateException()

            }
        }
    }
}
