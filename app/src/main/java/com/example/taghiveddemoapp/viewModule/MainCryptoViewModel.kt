package com.example.taghiveddemoapp.viewModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taghiveddemoapp.model.CryptoResponse
import com.example.taghiveddemoapp.model.GetCryptoResponse
import com.example.taghiveddemoapp.network.ApiRepository
import com.example.taghiveddemoapp.network.NetworkHelper
import com.example.taghiveddemoapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainCryptoViewModel @Inject constructor(
    private val repository: ApiRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {


    private val _cryptoGetResponse = MutableLiveData<NetworkResult<CryptoResponse>>()
    val finalCryptoResponse: LiveData<NetworkResult<CryptoResponse>> get() = _cryptoGetResponse


    private val _cryptoGetDetailResponse = MutableLiveData<NetworkResult<GetCryptoResponse>>()
    val cryptoGetDetailResponse: LiveData<NetworkResult<GetCryptoResponse>> get() = _cryptoGetDetailResponse


    fun getCryptoDetails(symbol: String) {

        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                _cryptoGetDetailResponse.postValue(NetworkResult.Loading())
                repository.getCryptoDetails(symbol).let {
                    if (it.isSuccessful) {
                        _cryptoGetDetailResponse.postValue(NetworkResult.Success(it.body()))

                    } else
                        _cryptoGetDetailResponse.postValue(NetworkResult.Error(it.message(), null))
                }
            } else
                _cryptoGetDetailResponse.postValue(
                    NetworkResult.Error(
                        "No internet connection",
                        null
                    )
                )
        }

    }

    fun getCryptoList() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                repository.getCryptos().let {
                    if (it.isSuccessful) {
                        _cryptoGetResponse.postValue(NetworkResult.Success(it.body()))
                    } else
                        _cryptoGetResponse.postValue(
                            NetworkResult.Error(
                                it.errorBody().toString(),
                                null
                            )
                        )
                }
            } else
                _cryptoGetResponse.postValue(NetworkResult.Error("No internet connection", null))
        }
    }


}