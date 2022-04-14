package com.example.taghiveddemoapp.viewModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taghiveddemoapp.model.CryptoResponse
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

    init {
        getCryptoList()
    }

    private val _cryptoGetResponse = MutableLiveData<Resource<CryptoResponse>>()
    val finalCryptoResponse: LiveData<Resource<CryptoResponse>>
        get() = _cryptoGetResponse

    private fun getCryptoList() {
        viewModelScope.launch {
           // _cryptoGetResponse.postValue(Resource.loading(null))
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