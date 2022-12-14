package com.pulmuone.store.qrmake

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pulmuone.store.qrmake.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val qrCodeUrl = "https://github.com/doriwori/qr-maker-sample"
        val dimension = 3000

        // FIXME:- overlayBitmap parameter를 지우면 기본 QR 이미지가 생성된다.
        val bitmap = qrCodeUrl.convertQrCode(
            dimension = dimension,
            overlayBitmap = getBitmapFromAssetImage(
                "sample_logo.png",
                dimension/3,
            )
        )

        if (bitmap != null) {
            binding.ivQrCode.setImageBitmap(bitmap)
        }
    }
}