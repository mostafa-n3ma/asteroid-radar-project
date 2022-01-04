package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroid_table")
data class Asteroid_Entity (
    @PrimaryKey
    val id:Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean)

fun List<Asteroid_Entity>.asDomainList():List<DomainObject>{
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