package com.pulmuone.store.qrmake

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import androidx.annotation.ColorInt
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

/**
 * String 을 QR Code 로 변환
 * @param dimension 가로,세로 사이즈
 * @param overlayBitmap 가운데에 추가할 오버레이 bitmap 이미지
 * @param color1 메인 컬러 (기본 검은색)
 * @param color2 서브 컬러 (기본 하얀색)
 * */
@Throws(WriterException::class)
fun String.convertQrCode(
    dimension: Int,
    overlayBitmap: Bitmap? = null,
    @ColorInt color1: Int = Color.BLACK,
    @ColorInt color2: Int = Color.WHITE
): Bitmap? {

    val result: BitMatrix
    try {
        result = MultiFormatWriter().encode(
            this,
            BarcodeFormat.QR_CODE,
            dimension,
            dimension,
            hashMapOf(EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H)
        )
    } catch (e: IllegalArgumentException) {
        return null // Unsupported format
    }

    val w = result.width
    val h = result.height
    val pixels = IntArray(w * h)
    for (y in 0 until h) {
        val offset = y * w
        for (x in 0 until w) {
            pixels[offset + x] = if (result.get(x, y)) color1 else color2
        }
    }
    val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    bitmap.setPixels(pixels, 0, dimension, 0, 0, w, h)

    return if (overlayBitmap != null) {
        bitmap.addOverlayToCenter(overlayBitmap)
    } else {
        bitmap
    }
}

/**
 * Asset Image를 Bitmap으로 변환
 * @param assetFileName 에셋 폴더에 있은 파일명
 * @param dimension 가로, 세로 사이즈
 * */
fun Context.getBitmapFromAssetImage(assetFileName: String, dimension: Int): Bitmap {
    return Bitmap.createScaledBitmap(
        BitmapFactory.decodeStream(
            resources.assets.open(assetFileName)
        ),
        dimension,
        dimension,
        true
    )
}

/**
 * Overlay 이미지 가운데 추가
 * @param overlayBitmap 오버레이 비트맵 이미지
 * */
fun Bitmap.addOverlayToCenter(overlayBitmap: Bitmap): Bitmap {

    val bitmap2Width = overlayBitmap.width
    val bitmap2Height = overlayBitmap.height
    val marginLeft = (this.width * 0.5 - bitmap2Width * 0.5).toFloat()
    val marginTop = (this.height * 0.5 - bitmap2Height * 0.5).toFloat()
    val canvas = Canvas(this)
    canvas.drawBitmap(this, Matrix(), null)
    canvas.drawBitmap(overlayBitmap, marginLeft, marginTop, null)
    return this
}


fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}