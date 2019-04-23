package com.mrecun.ui.activitis

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mrecun.R
import com.mrecun.adapter.PagerViewAdapter
import com.mrecun.databinding.ActivityMainBinding
import com.mrecun.model.PageModel
import com.mrecun.model.PagerListener
import com.mrecun.ui.fragments.PageFragment
import com.mrecun.ui.viewModels.MainActivityViewMode
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity<MainActivityViewMode>() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var pagerAdapter : PagerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setLayout(R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        subsctibeToView()
    }

    private fun startLoadData(){
        viewModel.updatePage(viewPager.currentItem)
    }

    override fun showProgress() {
        binding.loading = true
        binding.errorMessage = ""
    }

    override fun hideProgress() {
        binding.loading = false
        binding.errorMessage = ""
    }

    override fun showErrorMessage(message: String) {
        binding.loading = false
        binding.errorMessage = message
    }

    override fun getFactory(): ViewModelProvider.Factory = MainActivityViewMode.Factory(executor, service, this@MainActivity)

    override fun getViewModelClass(): Class<MainActivityViewMode> = MainActivityViewMode::class.java

    fun initPager(pages: List<PageModel>) {
        pagerAdapter = PagerViewAdapter(pages, supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(object : PagerListener() {
            override fun onPageChanged(position: Int) {
                viewModel.updatePage(position)
            }
        })
        tabLayout.setupWithViewPager(viewPager)
        startLoadData()
    }

    private fun subsctibeToView() {
        viewModel.data.observe(this, Observer {
            initPager(it)
        })

        viewModel.index.observe(this, Observer {
            val fragment = pagerAdapter.instantiateItem(viewPager, it) as PageFragment
            fragment.loadData()
        })
    }
}
