package com.udacity.asteroidradar.main

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.AsteroidAdapter
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.DomainObject


@BindingAdapter("imageOfDayText")
fun getImageText(textView: TextView,imgTitle:String){
    textView.text=imgTitle
}



@BindingAdapter("pictureOfTheDay")
fun getPicOfDay(imageView: ImageView,pictureOfDay: PictureOfDay?) {
    if (pictureOfDay?.mediaType=="image"){
        pictureOfDay.url.let {
            val imgUri = pictureOfDay.url.toUri().buildUpon().scheme("https").build()
            Glide.with(imageView.context)
                .load(imgUri)
                .into(imageView)
        }
    }

}



@BindingAdapter("bindCode_Name")
fun bindCodeName(textView: TextView,name:String){
    textView.text=name
    textView.contentDescription=name
}

@BindingAdapter("asteroidList")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DomainObject>?) {
    val adapter = recyclerView.adapter as AsteroidAdapter
    adapter.submitList(data)
}



@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription="Image ,the asteroid is potential hazardous"
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription="Image ,the asteroid is not potential hazardous"

    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription="Image ,the asteroid is potential hazardous"
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription="Image ,the asteroid is not potential hazardous"
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
    textView.contentDescription="absolute magnitude  is: ${textView.text}"
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
    textView.contentDescription="estimated diameter  is: ${textView.text}"

}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
    textView.contentDescription="velocity  is: ${textView.text}"


}
