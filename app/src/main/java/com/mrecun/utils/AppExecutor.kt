package com.mrecun.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class AppExecutor constructor(var diskIO: Executor, var networkIO: Executor, var mainThread: Executor) {

    companion object {

        var INSTANCE: AppExecutor? = null

        fun getAppExecutor(): AppExecutor {
            if (INSTANCE == null) {
                INSTANCE = AppExecutor()
            }
            return INSTANCE!!
        }
    }

    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecuter()
    )

    class MainThreadExecuter : Executor {
        var handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            handler.post(command)
        }

    }
}