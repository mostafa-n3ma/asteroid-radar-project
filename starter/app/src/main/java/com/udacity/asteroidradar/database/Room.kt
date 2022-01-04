package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AsteroidDao {

    @Query("select * from asteroid_table order by closeApproachDate asc")
    fun getAsteroids(): LiveData<List<Asteroid_Entity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroid(vararg asteroidEntity: Asteroid_Entity)

    @Query("delete from asteroid_table where closeApproachDate < :todayDate")
    fun deleteOldAsteroids(todayDate:String)

}

@androidx.room.Database(entities = [Asteroid_Entity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: Database
fun getDatabase(context: Context): Database {
    synchronized(Database::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                Database::class.java,
                "database"
            ).build()
        }
    }
    return INSTANCE
}