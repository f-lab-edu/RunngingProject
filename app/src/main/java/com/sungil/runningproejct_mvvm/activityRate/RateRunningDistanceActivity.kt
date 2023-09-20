package com.sungil.runningproejct_mvvm.activityRate

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sungil.runningproejct_mvvm.R
import com.sungil.runningproejct_mvvm.activityRate.viewModel.RateViewModel
import com.sungil.runningproejct_mvvm.databinding.ActivityRateBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RateRunningDistanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRateBinding
    private val viewModel: RateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        addListener()
    }

    private fun addListener() {
        binding.btnRate.setOnClickListener {
            when (binding.btnRate.text) {
                getString(R.string.btn_rate) -> {
                    binding.txtRate.text = getString(R.string.txt_rate_start)
                    binding.btnRate.text = getString(R.string.btn_stop_rate)
                    viewModel.startRunningRate()
                }

                getString(R.string.btn_stop_rate) -> {
                    binding.btnRate.text = getString(R.string.btn_rate)
                    viewModel.stopRunningRate(binding.txtRate.text.toString())
                }
            }
        }
    }

    private fun bindObserver() {
        viewModel.liveData.observe(this, Observer {
            when (it) {
                is RateViewModel.ViewStatus.ViewLoading -> {
                    Timber.d("Loading for get Distance")
                }

                is RateViewModel.ViewStatus.KmRate -> {
                    binding.txtRate.text = it.data
                }

                is RateViewModel.ViewStatus.ViewError -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}