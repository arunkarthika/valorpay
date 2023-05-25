package com.example.valorpay.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.valorpay.service.ApiService
import com.example.valorpay.model.Comments
import com.example.valorpay.model.ErrorModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import com.example.valorpay.model.Post
import com.example.valorpay.service.NetworkResponseCallback
import com.example.valorpay.util.AppConstant
import com.google.gson.Gson

class PostRepository @Inject constructor(
    private val apiService: ApiService
) {

    private lateinit var mCallback: NetworkResponseCallback

    fun getPost(
        callback: NetworkResponseCallback,
        userId:String,
    ): MutableLiveData<Post> {

        mCallback = callback
        val commentsMutableLiveData: MutableLiveData<Post> = MutableLiveData()

        val call: Call<Post> = apiService.getUserPost(userId)

        call.enqueue(object : Callback<Post> {

            override fun onFailure(call: Call<Post>, t: Throwable) {
                mCallback.onNetworkFailure(t)
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    commentsMutableLiveData.postValue(response.body()!!)
                    mCallback.onNetworkSuccess()
                } else {
                    try {
                        val error= Gson()
                        val commonError=error.fromJson(response.errorBody()!!.charStream(), ErrorModel::class.java)
                        mCallback.onNetworkFailed(commonError.error)
                    }catch (e:java.lang.Exception){
                        mCallback.onNetworkFailed(AppConstant.SomethingWentWrong)
                    }
                }
            }
        })
        return commentsMutableLiveData
    }




    fun getComments(
        callback: NetworkResponseCallback,
        userId:String,
    ): MutableLiveData<Comments> {

        mCallback = callback
        val commentsMutableLiveData: MutableLiveData<Comments> = MutableLiveData()

        val call: Call<Comments> = apiService.getComment(userId)

        call.enqueue(object : Callback<Comments> {

            override fun onFailure(call: Call<Comments>, t: Throwable) {
                mCallback.onNetworkFailure(t)
            }

            override fun onResponse(call: Call<Comments>, response: Response<Comments>) {
                if (response.isSuccessful) {
                    commentsMutableLiveData.postValue(response.body()!!)
                    mCallback.onNetworkSuccess()
                } else {
                   try {
                       val error= Gson()
                       val commonError=error.fromJson(response.errorBody()!!.charStream(), ErrorModel::class.java)
                       mCallback.onNetworkFailed(commonError.error)
                   }catch (e:java.lang.Exception){
                       mCallback.onNetworkFailed(AppConstant.SomethingWentWrong)
                   }
                }
            }
        })
        return commentsMutableLiveData
    }

}