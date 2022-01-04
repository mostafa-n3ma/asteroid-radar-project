package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.apiService
import com.udacity.asteroidradar.database.asAsteroid
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, Factory(activity.application)).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.asteroidRecycler.adapter=AsteroidAdapter(OnAsteroidClickListener {
            val asteroid: Asteroid =it.asAsteroid()
            viewModel.displayAsteroidDetails(asteroid)
        })
        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, Observer {
            if (null !=it){
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidDetailsCompleted()
            }
        })
        viewModel.pictureOfTheDay.observe(viewLifecycleOwner, Observer {
            binding.pictureofDay=it
        })




            setHasOptionsMenu(true)
            return binding.root
        }



        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.main_overflow_menu, menu)
            super.onCreateOptionsMenu(menu, inflater)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return true
        }
    }
