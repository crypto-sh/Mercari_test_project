package com.mrecun.data

import androidx.lifecycle.MutableLiveData
import com.github.library.RestClient
import com.mrecun.api.pageDataService
import com.mrecun.api.tabService
import com.mrecun.model.NetworkState
import com.mrecun.model.PageModel
import com.mrecun.model.Product
import com.mrecun.utils.AppExecutor


class Repository(
    private val executor: AppExecutor,
    private val service: RestClient
) {

    private val networkState = MutableLiveData<NetworkState>()

    fun mainPage(): MainPageResult {
        val data = MutableLiveData<List<PageModel>>()
        networkState.postValue(NetworkState.LOADING)
        tabService(appExecutor = executor,
            service = service,
            onSuccess = {
                data.postValue(it)
                networkState.postValue(NetworkState.LOADED)
            }, onError = {
                networkState.postValue(NetworkState.error(it))
            })
        return MainPageResult(data, networkState)
    }

    fun getPageData(pageModel: PageModel): PageDataResult {
        val data = MutableLiveData<List<Product>>()
        networkState.postValue(NetworkState.LOADING)
        pageDataService(appExecutor = executor,
            service = service,
            url = pageModel.data,
            onSuccess = {
                data.postValue(it)
                networkState.postValue(NetworkState.LOADED)
            }, onError = {
                networkState.postValue(NetworkState.error(it))
            })
        return PageDataResult(data, networkState)
    }

}