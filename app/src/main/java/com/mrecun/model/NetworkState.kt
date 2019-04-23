package com.mrecun.model

enum class State {
    RUNNING,
    LOADED,
    FAILED
}

data class NetworkState(
    val state: State,
    val msg : String? = null){

    companion object {
        val LOADED  = NetworkState(State.LOADED,"")
        val LOADING = NetworkState(State.RUNNING,"")
        fun error(message : String?) = NetworkState(State.FAILED,message)
    }
}