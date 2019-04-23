package com.mrecun.ui.activitis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.library.RestClient
import com.mrecun.ui.viewModels.BaseView
import com.mrecun.ui.viewModels.BaseViewModel
import com.mrecun.utils.AppExecutor
import org.koin.android.ext.android.inject


abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    val executor : AppExecutor by inject()

    val service  : RestClient by inject()

    public lateinit var viewModel : T

    protected fun <T : ViewDataBinding> setLayout(layoutId: Int): T {
        return DataBindingUtil.setContentView(this, layoutId)
    }

    protected fun <T : ViewDataBinding> setLayout(activity: AppCompatActivity, layoutId: Int): T {
        return DataBindingUtil.setContentView(activity, layoutId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this,getFactory()).get(getViewModelClass())
        viewModel.getNetworkStatusEvent().observe(this, Observer {
            when(it){
                is BaseView.ShowProgress -> {
                    showProgress()
                }
                is BaseView.HideProgress -> {
                    hideProgress()
                }
                is BaseView.ShowErrorMessage -> {
                    showErrorMessage(it.errorMessage)
                }
            }
        })
    }

    abstract fun getFactory() : ViewModelProvider.Factory

    abstract fun getViewModelClass() : Class<T>

    abstract fun showProgress()

    abstract fun hideProgress()

    abstract fun showErrorMessage(message: String)

}