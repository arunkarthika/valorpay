package com.example.valorpay.service

interface NetworkResponseCallback {
    fun onNetworkSuccess()
    fun onNetworkFailed(msg:String)
    fun onNetworkFailure(th : Throwable)
}