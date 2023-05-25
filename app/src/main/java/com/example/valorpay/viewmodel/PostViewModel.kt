package com.example.valorpay.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.valorpay.model.Post
import com.example.valorpay.repository.PostRepository
import com.example.valorpay.service.NetworkResponseCallback
import com.example.valorpay.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel  @Inject constructor(
    private val repository: PostRepository,
    private val app: Application

) : ViewModel() {
    private var liveDataList: MutableLiveData<Post> = MutableLiveData()
    val mShowProgressBar = MutableLiveData(false)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()

    fun getLiveData(): MutableLiveData<Post> {
        return liveDataList
    }

    fun loadPost(userId:String) {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            liveDataList=  repository.getPost(object : NetworkResponseCallback {
                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                }
                override fun onNetworkFailed(msg: String) {
                    mShowProgressBar.value = false
                    mShowApiError.value = msg
                }
                override fun onNetworkFailure(th: Throwable) {
                    mShowProgressBar.value = false
                    mShowApiError.value = th.message
                }
            }, userId)
        } else {
            mShowProgressBar.value = false
            mShowNetworkError.value = true
        }

    }
}