package com.mrecun.ui.viewModels

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.library.RestClient
import com.mrecun.data.Repository
import com.mrecun.model.PageModel
import com.mrecun.utils.AppExecutor
import com.mrecun.utils.BaseApp

class PageFragmentViewModel(private val repository: Repository) : ViewModel() {

    val page = MutableLiveData<PageModel>()

    private val repo = Transformations.map(page){
        repository.getPageData(it)
    }

    val data   = Transformations.switchMap(repo){
        it.data
    }

    val network = Transformations.switchMap(repo){
        it.networkStatus
    }


    fun updatePageData(pageModel: PageModel){
        page.postValue(pageModel)
    }

    class Factory(
        private val executor: AppExecutor,
        private val service : RestClient
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            return PageFragmentViewModel(Repository(executor, service)) as T
        }
    }
}