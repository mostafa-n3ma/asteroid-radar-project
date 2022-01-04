package com.udacity.asteroidradar

import android.os.Parcelable
import com.udacity.asteroidradar.database.Asteroid_Entity
import com.udacity.asteroidradar.database.DomainObject
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(val id: Long,
                    val codename: String,
                    val closeApproachDate: String,
                    val absoluteMagnitude: Double,
                    val estimatedDiameter: Double,
                    val relativeVelocity: Double,
                    val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable

fun List<Asteroid>.asEntityList():Array<Asteroid_Entity>{
    return map {
        Asteroid_Entity(
            id = it.id,
            relativeVelocity = it.relativeVelocity,
            isPotentiallyHazardous = it.isPotentiallyHazardous,
            estimatedDiameter = it.estimatedDiameter,
            distanceFromEarth = it.distanceFromEarth,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            codename = it.codename
        )
    }.toTypedArray()
}
fun List<Asteroid>.asDomainList():List<DomainObject>{
    return map {
        DomainObject(
            id = it.id,
            codename = it.codename,
            absoluteMagnitude = it.absoluteMagnitude,
            closeApproachDate = it.closeApproachDate,
            distanceFromEarth = it.distanceFromEarth,
            estimatedDiameter = it.estimatedDiameter,
            isPotentiallyHazardous = it.isPotentiallyHazardous,
            relativeVelocity = it.relativeVelocity
        )
    }
}