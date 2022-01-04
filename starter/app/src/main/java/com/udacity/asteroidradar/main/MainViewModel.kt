package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.database.DomainObject
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.apiService
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repo = AsteroidRepository(database)
    private val TAG = "MainViewModel"

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay> get() = _pictureOfTheDay


    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
    val navigateToSelectedAsteroid: LiveData<Asteroid> get() = _navigateToSelectedAsteroid

    fun displayAsteroidDetails(asteroid: Asteroid){
        _navigateToSelectedAsteroid.value=asteroid
    }
    fun displayAsteroidDetailsCompleted(){
        _navigateToSelectedAsteroid.value=null
    }
    init {
        getPictureOfTheDay()
        viewModelScope.launch {
            repo.refreshAsteroidData()
        }
    }

    val asteroidlist: LiveData<List<DomainObject>> = repo.asteroids

    private fun getPictureOfTheDay() {
        viewModelScope.launch {
            try {
                _pictureOfTheDay.value = apiService.services.getImageOfTheDay(
                    Constants.API_KEY
                )
                Log.i(TAG, "getPictureOfTheDay: succuss getting image")
            } catch (e1: Exception) {
                Log.i(TAG, "getPictureOfTheDay: Faile gitting image")
            }
        }
    }

}

class Factory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(app) as T
        } else {
            throw IllegalArgumentException("unable to construct viewmodel")
        }
    }

}