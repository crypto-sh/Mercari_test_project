package com.mrecun.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mrecun.R
import com.mrecun.model.ImageShape
import com.mrecun.utils.GlideApp


class ImageViewCustom : AppCompatImageView {

    //default image is square type in this class
    private var imageShape: ImageShape = ImageShape.SQUARE

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typed = context?.obtainStyledAttributes(attrs, R.styleable.ImageViewCustom)
        val shape = typed?.getInt(R.styleable.ImageViewCustom_imageShape, 0)
        imageShape = ImageShape(ImageShape.convert(shape!!))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        when (imageShape) {
            ImageShape.SQUARE -> {
                super.onMeasure(widthMeasureSpec, widthMeasureSpec)
            }
            else -> {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            }
        }
    }

    fun setImage(url: String) {
        val loader = GlideApp.with(this.context)
            .load(url)
        when (imageShape) {
            ImageShape.SQUARE -> {
                loader
                    .placeholder(R.drawable.placeholder)
                    .fitCenter()
            }
            ImageShape.CIRCLE -> {
                loader
                    .placeholder(R.drawable.circle_placeholder)
                    .fitCenter()
                    .optionalCircleCrop()
            }
            ImageShape.ROUNDED -> {
                loader
                    .placeholder(com.mrecun.R.drawable.placeholder)
                    .fitCenter()
                    .apply(RequestOptions().transform(RoundedCorners(convertPixelsToDp(10))))
            }
        }
        loader.into(this)
    }

    fun convertPixelsToDp(px: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        val dp = Math.round((px / (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).toDouble())
        return dp.toInt()
    }

}