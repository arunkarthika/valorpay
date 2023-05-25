package com.example.valorpay.service

import com.example.valorpay.model.Comments
import com.example.valorpay.model.Post
import com.example.valorpay.model.UserModel
import com.example.valorpay.model.UserModelItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    fun getData(): Call<UserModel>

    @GET("users/{user}/posts")
    fun getUserPost(@Path("user") user: String): Call<Post>

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Call<UserModelItem>

    @GET("posts/{postId}/comments")
    fun getComment(@Path("postId") postId: String): Call<Comments>
}