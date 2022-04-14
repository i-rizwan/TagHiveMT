package com.example.taghiveddemoapp.model

import com.google.gson.annotations.SerializedName

class CryptoResponse : ArrayList<CryptoResponseItem>()

data class CryptoResponseItem(
    @SerializedName("lastPrice")
    val lastPrice: String? = null,
    @SerializedName("quoteAsset")
    val quoteAsset: String? = null,
    @SerializedName("symbol")
    val symbol: String? = null,
    @SerializedName("baseAsset")
    val baseAsset: String? = null,
    @SerializedName("volume")
    val volume: String? = null
)