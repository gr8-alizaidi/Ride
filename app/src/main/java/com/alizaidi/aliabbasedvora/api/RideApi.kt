package com.alizaidi.aliabbasedvora.api

import com.alizaidi.aliabbasedvora.Ride
import com.alizaidi.aliabbasedvora.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RideApi {

    @Headers("Content-Type: application/json")
   @GET("/rides")
    suspend fun getRide(): Response<List<Ride>>

    @Headers("Content-Type: application/json")
    @GET("/user")
    suspend fun getUser(): Response<User>

}