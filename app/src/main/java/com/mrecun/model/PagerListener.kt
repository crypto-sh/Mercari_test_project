package com.mrecun.model

import androidx.viewpager.widget.ViewPager

abstract class PagerListener : ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
        //don't need implement
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //don't need implement
    }

    override fun onPageSelected(position: Int) {
        onPageChanged(position)
    }

    abstract fun onPageChanged(position : Int)
}
