package com.alizaidi.aliabbasedvora.retrofit

import com.alizaidi.aliabbasedvora.api.RideApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object instance {

    val mainInstance: RideApi = Retrofit.Builder()
        .baseUrl("https://assessment.api.vweb.app/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RideApi::class.java)

}

