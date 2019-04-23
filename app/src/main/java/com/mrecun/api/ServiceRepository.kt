package com.mrecun.api

import android.annotation.SuppressLint
import android.content.Context
import com.github.library.RestClient
import com.github.library.response.ResponseJsonHandler
import com.google.gson.Gson
import com.mrecun.api.ServiceRepository.Companion.mainUrl
import com.mrecun.model.PageModel
import com.mrecun.model.Product
import com.mrecun.utils.AppExecutor
import org.json.JSONArray
import org.json.JSONObject


fun tabService(
    appExecutor: AppExecutor,
    service: RestClient,
    onSuccess: (List<PageModel>) -> Unit,
    onError: (String) -> Unit
) {
    appExecutor.networkIO.execute {
        service.GET(mainUrl, "", object : ResponseHandler() {
            override fun onResultSuccess(result: String) {
                appExecutor.mainThread.execute {
                    try {
                        val response = Gson().fromJson(result, Array<PageModel>::class.java).toList()
                        onSuccess(response)
                    } catch (e: Exception) {
                        onError(e.message.toString())
                    }
                }
            }

            override fun onResultError(message: String) {
                appExecutor.mainThread.execute { onError(message) }
            }
        })
    }
}


fun pageDataService(
    appExecutor: AppExecutor,
    service: RestClient,
    url: String,
    onSuccess: (List<Product>) -> Unit,
    onError: (String) -> Unit
) {
    appExecutor.networkIO.execute {
        service.GET(url, "", object : ResponseHandler() {
            override fun onResultSuccess(result: String) {
                appExecutor.mainThread.execute {
                    try {
                        val response = Gson().fromJson(result, Array<Product>::class.java).toList()
                        onSuccess(response)
                    } catch (e: Exception) {
                        onError(e.message.toString())
                    }
                }
            }

            override fun onResultError(message: String) {
                appExecutor.mainThread.execute { onError(message) }
            }
        })
    }
}

interface ServiceRepository {

    companion object {
        const val mainUrl = "https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/master.json"

        @SuppressLint("StaticFieldLeak")
        var INSTANCE : RestClient? = null

        fun create(context: Context): RestClient {
            if (INSTANCE == null){
                INSTANCE = RestClient.Builder(context)
                    .setDebugEnable(true)
                    .build()
            }
            return INSTANCE!!
        }
    }
}


abstract class ResponseHandler : ResponseJsonHandler() {

    override fun onSuccess(result: JSONObject?) {
        onSuccess(result.toString())
    }

    override fun onSuccess(result: JSONArray?) {
        onSuccess(result.toString())
    }

    override fun onSuccess(result: String?) {
        if (result != null) {
            onResultSuccess(result)
        } else {
            onResultError("unknown message")
        }
    }

    override fun onFailure(errorCode: Int, errorMsg: String?) {
        onResultError(errorMsg ?: "unknown message")
    }

    abstract fun onResultSuccess(result: String)
    abstract fun onResultError(message: String)
}
