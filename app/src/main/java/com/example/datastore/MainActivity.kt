package com.example.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import com.example.datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var userDetails: UserDetails
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        userDetails = UserDetails(this)

        binding.saveData.setOnClickListener {
            CoroutineScope(IO).launch {
                userDetails.storeDetails(
                    binding.enterName.text.toString().trim(),
                    binding.enterAge.text.toString().trim().toInt()
                )
            }

        }

        lifecycle.coroutineScope.launchWhenCreated {
            userDetails.getName().collect {
                binding.name.text = it
            }
        }

        lifecycle.coroutineScope.launchWhenCreated {
            userDetails.getAge().collect {
                binding.age.text = it.toString()
            }
        }

    }
}