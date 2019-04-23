package com.mrecun.utils

import android.app.Application
import com.mrecun.api.ServiceRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BaseApp : Application() {

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    private external fun stringSample() : String

    private val appModule = module {

        single { AppExecutor() }
        factory { ServiceRepository.create(get()) }
    }

    override fun onCreate() {
        super.onCreate()


        /**
         *
         * this is just sample for JNI
         * you can read more about it in readme.md
         * */
        System.out.println(stringSample())


        startKoin {
            printLogger()
            androidContext(this@BaseApp)
            modules(appModule)

        }

    }

}