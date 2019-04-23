package com.mrecun.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.mrecun.ui.customView.ImageViewCustom

class BindingAdapter {

    companion object {

        @BindingAdapter("VisibleOrGone")
        @JvmStatic fun View.showVisibility(loading : Boolean){
            this.visibility = if (loading) View.VISIBLE  else View.GONE
        }

        @BindingAdapter("ImageUrl")
        @JvmStatic fun ImageViewCustom.setImageUrl(photo : String){
            this.setImage(photo)
        }
    }
}