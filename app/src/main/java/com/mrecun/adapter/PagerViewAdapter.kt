package com.mrecun.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mrecun.model.PageModel
import com.mrecun.ui.fragments.BaseFragment
import com.mrecun.ui.fragments.PageFragment

class PagerViewAdapter(
    private val pages           : List<PageModel>,
    fragmentManager : FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return PageFragment.newInstance(pages[position])
    }

    override fun getCount(): Int = pages.size

    override fun getPageTitle(position: Int): CharSequence? = pages[position].name

}