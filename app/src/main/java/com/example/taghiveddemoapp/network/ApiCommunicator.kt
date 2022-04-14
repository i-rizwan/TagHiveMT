package com.example.taghiveddemoapp.network

import com.example.taghiveddemoapp.model.CryptoResponse
import com.example.taghiveddemoapp.model.GetCryptoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCommunicator {

    @GET("tickers/24hr")
    suspend fun getCryptos(): Response<CryptoResponse>

    @GET("ticker/24hr")
    suspend fun getCryptoDetails(@Query("symbol") symbol: String): Response<GetCryptoResponse>





}