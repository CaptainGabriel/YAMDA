package com.dev.moviedb.mvvm.extensions

import android.graphics.*
import com.squareup.picasso.Transformation

/**
 *
 * Yambda 1.1.0
 */
class RoundedTransformation(var radius: Int, var margin: Int) : Transformation {

    override fun transform(source: Bitmap?): Bitmap {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.setShader(BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))

        val bitmap = Bitmap.createBitmap(source!!.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), (source.width - margin).toFloat(), (source.height - margin).toFloat()), radius.toFloat(), radius.toFloat(), paint)

        if (source != bitmap) {
            source.recycle()
        }

        return bitmap
    }

    override fun key(): String {
        return "rounded"
    }
}