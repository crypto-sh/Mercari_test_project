package com.mrecun.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.library.RestClient
import com.mrecun.utils.AppExecutor
import org.koin.android.ext.android.inject


abstract class BaseFragment<T : ViewModel> : Fragment(){

    lateinit var viewModel : T

    val executor : AppExecutor by inject()

    val service  : RestClient by inject()

    protected fun <T : ViewDataBinding> setLayout(inflater: LayoutInflater,layoutId: Int, parent : ViewGroup?): T {
        return DataBindingUtil.inflate(inflater,layoutId,parent,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this,getFactory()).get(getViewModelClass())
    }


    protected fun isViewModelInitialized() : Boolean = ::viewModel.isInitialized

    abstract fun getFactory() : ViewModelProvider.Factory

    abstract fun getViewModelClass() : Class<T>
}