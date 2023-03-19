package com.example.education.di

import com.example.education.data.bd.DatabaseHandler
import com.example.education.data.bd.repository.WeatherRepositoryLocal
import com.example.education.data.network.OpenWeatherService
import com.example.education.data.network.repository.WeatherRepositoryRemote
import com.example.education.domain.interactor.WeatherGetByCityInteractor
import com.example.education.domain.usecase.WeatherByCityUseCase
import com.example.education.domain.usecase.WeatherByCoordinateUseCase
import com.example.education.domain.usecase.WeatherSaveUseCase

object ServiceLocator {
    // Source
    private val localSource = DatabaseHandler.roomDatabase!!
    private val remoteSourceWeather = OpenWeatherService.getInstance()

    // Repository
    private val weatherRepositoryLocal = WeatherRepositoryLocal(localSource)
    private val weatherRepositoryRemote = WeatherRepositoryRemote(remoteSourceWeather)

    // UseCase
    private val weatherByCityUseCase =
        WeatherByCityUseCase(weatherRepositoryLocal, weatherRepositoryRemote)

    val weatherByCoordinateUseCase = WeatherByCoordinateUseCase(weatherRepositoryRemote)

    private val weatherSaveUseCase = WeatherSaveUseCase(weatherRepositoryLocal)

    // Interactors
    val weatherGetByCityInteractor =
        WeatherGetByCityInteractor(weatherByCityUseCase, weatherSaveUseCase)
}
