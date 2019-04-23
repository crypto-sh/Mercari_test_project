package com.mrecun.ui.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.github.library.RestClient
import com.mrecun.data.Repository
import com.mrecun.model.NetworkState
import com.mrecun.model.PageModel
import com.mrecun.utils.AppExecutor

class MainActivityViewMode(
    private val repository: Repository,
    private val lifecycleOwner: LifecycleOwner
) : BaseViewModel(lifecycleOwner) {

    val index = MutableLiveData<Int>()

    private val startLoading = MutableLiveData<Boolean>()

    private val repo = Transformations.map(startLoading) {
        repository.mainPage()
    }

    val data: LiveData<List<PageModel>> = Transformations.switchMap(repo) {
        it.data
    }

    override fun getNetworkStatus(): LiveData<NetworkState> = Transformations.switchMap(repo) {
        it.networkStatus
    }

    init {
        startLoading.postValue(true)
    }

    fun updatePage(position: Int) {
        index.postValue(position)
    }

    fun checkStartingValue() = startLoading.value

    class Factory(
        private val executor: AppExecutor,
        private val service : RestClient ,
        private val activity : AppCompatActivity
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewMode(Repository(executor, service), activity) as T
        }
    }
}