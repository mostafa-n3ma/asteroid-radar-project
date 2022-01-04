package com.udacity.asteroidradar.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.apiService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asEntityList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository (private val database: Database){
    private val TAG = "MainViewModel"
    val asteroids: LiveData<List<DomainObject>> = Transformations.map(database.asteroidDao.getAsteroids()){
        it.asDomainList()
    }

    suspend fun refreshAsteroidData(){
        withContext(Dispatchers.IO){
            try {

                val calendar = Calendar.getInstance()
                val dateFormat =
                    SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
                val currentDay = dateFormat.format(calendar.time)
                calendar.add(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS)
                val lastDay = dateFormat.format(calendar.time)
                var resultData =
                    apiService.services.getAsteroids(currentDay, lastDay, Constants.API_KEY)
                val asteroidList = resultData.let { parseAsteroidsJsonResult(JSONObject(it)) }
                database.asteroidDao.insertAsteroid(*asteroidList.asEntityList())
                database.asteroidDao.deleteOldAsteroids(currentDay)
            }catch (e:Exception){
                Log.e(TAG, "refreshAsteroidData: ", e.cause)
            }
        }
    }


}