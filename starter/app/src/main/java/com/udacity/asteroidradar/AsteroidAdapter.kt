package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.database.DomainObject
import com.udacity.asteroidradar.databinding.AsteroidItemBinding

class AsteroidAdapter (val clickListener: OnAsteroidClickListener):ListAdapter<DomainObject,AsteroidAdapter.AsteroidViewHolder>(AsteroidDiffCallback) {
    class AsteroidViewHolder(val binding:AsteroidItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bindAsteroid(asteroid:DomainObject){
            binding.domainAsteroid=asteroid
            binding.executePendingBindings()
        }
    }

    companion object AsteroidDiffCallback : DiffUtil.ItemCallback<DomainObject>() {
      override fun areItemsTheSame(oldItem: DomainObject, newItem: DomainObject): Boolean {
          return oldItem===newItem
      }

      override fun areContentsTheSame(oldItem: DomainObject, newItem: DomainObject): Boolean {
          return oldItem==newItem
      }
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(AsteroidItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
       holder.bindAsteroid(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick(getItem(position))
        }
    }



}
class OnAsteroidClickListener(val clicklistener:(Asteroid:DomainObject)->Unit){
    fun onClick(asteroid: DomainObject)=clicklistener(asteroid)
}