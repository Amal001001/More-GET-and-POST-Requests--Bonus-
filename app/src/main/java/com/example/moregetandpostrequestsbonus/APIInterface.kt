package com.example.moregetandpostrequestsbonus

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
    @GET("/test/")
    fun getUsers(): Call<ArrayList<usersInfoItem>?>?

    @POST("/test/")
    fun addUser(@Body newUser:usersInformation): Call<usersInformation>
}