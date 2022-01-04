package com.udacity.asteroidradar

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.database.getDatabase
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class RefreshDataWorker (appContext: Context,parameters: WorkerParameters):CoroutineWorker(appContext,parameters) {
    companion object{
        const val WORK_NAME="refresh data base"

        val constraints=Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                    setRequiresDeviceIdle(true)
            }.build()
        val repeatingRequest= PeriodicWorkRequestBuilder<RefreshDataWorker>(
            1,TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()





    }
    override suspend fun doWork(): Result {
        val database= getDatabase(applicationContext)
        val repo=AsteroidRepository(database)
        return try {
            repo.refreshAsteroidData()
             Result.success()
        }catch (e:HttpException){
             Result.retry()
        }
    }
}
