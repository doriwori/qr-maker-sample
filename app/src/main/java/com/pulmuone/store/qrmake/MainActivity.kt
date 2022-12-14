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


        val qrCodeUrl = "https://pmoo365.sharepoint.com/sites/appstore"
        val dimension = 3000

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