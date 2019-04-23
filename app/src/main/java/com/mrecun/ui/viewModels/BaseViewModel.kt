package com.mrecun.ui.viewModels

import androidx.lifecycle.*
import com.mrecun.model.NetworkState
import com.mrecun.model.State
import com.mrecun.utils.SingleLiveEvent


abstract class BaseViewModel(private val lifecycleOwner : LifecycleOwner) : ViewModel(), LifecycleObserver{

    abstract fun getNetworkStatus(): LiveData<NetworkState>

    private val networkEvent: SingleLiveEvent<BaseView> = SingleLiveEvent()

    fun getNetworkStatusEvent() : SingleLiveEvent<BaseView> = networkEvent

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
        val network = getNetworkStatus()
        network.observe(lifecycleOwner, Observer {
            handleNetworkState(it!!)
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    private fun handleNetworkState(it : NetworkState){
        when (it.state) {
            State.RUNNING -> {
                getNetworkStatusEvent().postValue(BaseView.ShowProgress())
            }
            State.LOADED -> {
                getNetworkStatusEvent().postValue(BaseView.HideProgress())
            }
            else -> {
                getNetworkStatusEvent().postValue(BaseView.ShowErrorMessage(it.msg!!))
            }
        }
    }



}
