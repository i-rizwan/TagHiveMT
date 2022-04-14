package com.example.taghiveddemoapp.model

import com.google.gson.annotations.SerializedName

data class GetCryptoResponse(
    @SerializedName("highPrice")
    val highPrice: String? =  null,
    @SerializedName("lastPrice")
    val lastPrice: String? =  null,
    @SerializedName("lowPrice")
    val lowPrice: String? =  null,
    @SerializedName("openPrice")
    val openPrice: String? =  null,
    @SerializedName("quoteAsset")
    val quoteAsset: String? =  null,
    @SerializedName("symbol")
    val symbol: String? =  null,
    @SerializedName("baseAsset")
    val baseAsset: String? =  null,
    @SerializedName("volume")
    val volume: String? =  null
)