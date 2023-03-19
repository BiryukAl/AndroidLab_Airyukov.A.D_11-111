package com.example.education.di

import androidx.lifecycle.viewmodel.CreationExtras
import com.example.education.domain.interactor.WeatherGetByCityInteractor
import com.example.education.domain.usecase.WeatherByCoordinateUseCase

object ViewModelArgsKeys {

    val weatherGetByCityInteractor = object : CreationExtras.Key<WeatherGetByCityInteractor> {}
    val weatherByCoordinateUseCase = object : CreationExtras.Key<WeatherByCoordinateUseCase> {}
}
