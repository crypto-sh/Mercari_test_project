package com.mrecun.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mrecun.model.NetworkState
import com.mrecun.model.PageModel
import com.mrecun.model.Product

data class MainPageResult(
    val data : LiveData<List<PageModel>>,
    val networkStatus : MutableLiveData<NetworkState>
)


data class PageDataResult(
    val data : LiveData<List<Product>>,
    val networkStatus : MutableLiveData<NetworkState>
)