package com.sungil.runningproejct_mvvm.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sungil.runningproejct_mvvm.R
import com.sungil.runningproejct_mvvm.databinding.ActivityMainBinding
import com.sungil.runningproejct_mvvm.main.viewModel.MainViewModel
import com.sungil.runningproejct_mvvm.utill.SetOnClickListener
import com.sungil.runningproejct_mvvm.utill.PostAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //viewModel hilt 적용
    private val viewModel: MainViewModel by viewModels()
    private val postAdapter = PostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initFollower()
        addListener()
    }

    private fun initView(){
        val layoutManger : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerviewContent.layoutManager = layoutManger
        binding.txtOtherSns.setTextColor(getColor(R.color.gray))
    }

    private fun initFollower(){
        viewModel.getFollower()
    }

    private fun addListener() {
        viewModel.wearLiveData.observe(this, Observer {
            val data = it ?: return@Observer
            Timber.d("The Data is Comming $data")
        })


        binding.txtMySns.setOnClickListener {
            binding.txtOtherSns.setTextColor(getColor(R.color.gray))
            binding.txtMySns.setTextColor(getColor(R.color.white))
            viewModel.getFollowerPost()
        }

        binding.txtOtherSns.setOnClickListener {
            binding.txtMySns.setTextColor(getColor(R.color.gray))
            binding.txtOtherSns.setTextColor(getColor(R.color.white))
            viewModel.getUnFollowerPost()
        }

        viewModel.followerPostLiveData.observe(this, Observer {
            Timber.d("The Follower Post is Come")
            postAdapter.data = it
            binding.recyclerviewContent.adapter = postAdapter
        })

        viewModel.unFollowerPostLiveData.observe(this, Observer {
            Timber.d("The UnFollower Post is Come")
            postAdapter.data = it
            binding.recyclerviewContent.adapter = postAdapter
        })

        viewModel.setFollowerLiveData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(this , it , Toast.LENGTH_SHORT).show()
        })

        postAdapter.setOnClickListener(object : SetOnClickListener {
            override fun onValueClick(data: String) {
                Timber.d("user Select Follower $data")
                viewModel.writeNewFollower(data)
            }
        })
    }
}