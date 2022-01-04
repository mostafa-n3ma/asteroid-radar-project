package com.udacity.asteroidradar.database

import androidx.lifecycle.Transformations.map
import com.udacity.asteroidradar.Asteroid

data class DomainObject ( val id:Long,
                          val codename: String,
                          val closeApproachDate: String,
                          val absoluteMagnitude: Double,
                          val estimatedDiameter: Double,
                          val relativeVelocity: Double,
                          val distanceFromEarth: Double,
                          val isPotentiallyHazardous: Boolean)
fun DomainObject.asAsteroid(): Asteroid {
    return let {
        Asteroid(
            id=it.id,
            codename = it.codename,
            absoluteMagnitude = it.absoluteMagnitude,
            closeApproachDate = it.closeApproachDate,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous,
            relativeVelocity = it.relativeVelocity,
            estimatedDiameter = it.estimatedDiameter
        )
    }
}

