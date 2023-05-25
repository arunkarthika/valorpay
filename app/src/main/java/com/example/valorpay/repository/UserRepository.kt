package com.example.valorpay.repository

import androidx.lifecycle.MutableLiveData
import com.example.valorpay.model.ErrorModel
import com.example.valorpay.model.Post
import com.example.valorpay.service.ApiService
import com.example.valorpay.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import com.example.valorpay.model.UserModelItem
import com.example.valorpay.service.NetworkResponseCallback
import com.example.valorpay.util.AppConstant
import com.google.gson.Gson

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {

    private lateinit var mCallback: NetworkResponseCallback


    fun getUserList(
        callback: NetworkResponseCallback,
    ): MutableLiveData<UserModel> {

        mCallback = callback
        val commentsMutableLiveData: MutableLiveData<UserModel> = MutableLiveData()

        val call: Call<UserModel> = apiService.getData()

        call.enqueue(object : Callback<UserModel> {

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                mCallback.onNetworkFailure(t)
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
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


    fun getUser(postMutableLiveData: MutableLiveData<UserModelItem>,userId:String,throwable: MutableLiveData<Throwable>) {
        val call: Call<UserModelItem> = apiService.getUser(userId)

        call.enqueue(object : Callback<UserModelItem> {

            override fun onFailure(call: Call<UserModelItem>, t: Throwable) {
                throwable.postValue(t)
                postMutableLiveData.postValue(null)
            }

            override fun onResponse(call: Call<UserModelItem>, response: Response<UserModelItem>) {
                postMutableLiveData.postValue(response.body())
            }
        })
    }

    fun getUser(
        callback: NetworkResponseCallback,
        userId: String
    ): MutableLiveData<UserModelItem> {

        mCallback = callback
        val commentsMutableLiveData: MutableLiveData<UserModelItem> = MutableLiveData()

        val call: Call<UserModelItem> = apiService.getUser(userId)

        call.enqueue(object : Callback<UserModelItem> {

            override fun onFailure(call: Call<UserModelItem>, t: Throwable) {
                mCallback.onNetworkFailure(t)
            }

            override fun onResponse(call: Call<UserModelItem>, response: Response<UserModelItem>) {
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