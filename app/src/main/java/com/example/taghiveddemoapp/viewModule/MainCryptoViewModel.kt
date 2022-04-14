package com.example.taghiveddemoapp.viewModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taghiveddemoapp.model.CryptoResponse
import com.example.taghiveddemoapp.model.GetCryptoResponse
import com.example.taghiveddemoapp.network.ApiRepository
import com.example.taghiveddemoapp.network.NetworkHelper
import com.example.taghiveddemoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainCryptoViewModel @Inject constructor(
    private val repository: ApiRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {



    private val _cryptoGetResponse = MutableLiveData<Resource<CryptoResponse>>()
    val finalCryptoResponse: LiveData<Resource<CryptoResponse>> get() = _cryptoGetResponse


    private val _cryptoGetDetailResponse = MutableLiveData<Resource<GetCryptoResponse>>()
    val cryptoGetDetailResponse: LiveData<Resource<GetCryptoResponse>> get() = _cryptoGetDetailResponse


   fun getCryptoDetails(symbol: String) {

        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                repository.getCryptoDetails(symbol).let {
                    if (it.isSuccessful) {
                        _cryptoGetDetailResponse.postValue(Resource.success(it.body()))
                    } else _cryptoGetDetailResponse.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else _cryptoGetResponse.postValue(Resource.error("No internet connection", null))
        }

    }

   fun getCryptoList() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                repository.getCryptos().let {
                    if (it.isSuccessful) {
                        _cryptoGetResponse.postValue(Resource.success(it.body()))
                    } else _cryptoGetResponse.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else _cryptoGetResponse.postValue(Resource.error("No internet connection", null))
        }
    }


}