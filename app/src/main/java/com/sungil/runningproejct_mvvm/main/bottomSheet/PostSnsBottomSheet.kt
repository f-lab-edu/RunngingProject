package com.sungil.runningproejct_mvvm.main.bottomSheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sungil.runningproejct_mvvm.R
import com.sungil.device.entity.PostSnsBottomSheet
import com.sungil.runningproejct_mvvm.databinding.BottomSheetPostSnsBinding
import com.sungil.runningproejct_mvvm.utill.Define
import timber.log.Timber

class PostSnsBottomSheet(val value: (PostSnsBottomSheet) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetPostSnsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BottomSheetPostSnsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        addListener()
    }
    private fun init() {
        val km = arguments?.getString(Define.KM_KEY)
        if (km == null) {
            binding.txtKm.setText("0km")
            return
        }
        binding.txtKm.setText(km)
    }
    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(false)
    }

    private fun addListener() {
        binding.imgClose.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnPostSns.setOnClickListener {
            Timber.d("User Select Post SNS")
            val title = binding.txtContentTitle.text.toString().trim()
            val content = binding.txtContent.text.toString().trim()
            val km = binding.txtKm.text.toString().trim()
            when {
                title.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.msg_check_content),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                content.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.msg_check_content),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                km.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.msg_check_km),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val postData = PostSnsBottomSheet(title, content, km)
                    value(postData)
                    dialog?.dismiss()
                }
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        val postData = PostSnsBottomSheet(null, null, null)
        value(postData)
        dialog.dismiss()
    }

    override fun getTheme(): Int {
        return R.style.DialogCustomTheme
    }

}