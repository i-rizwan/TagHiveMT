package com.example.taghiveddemoapp.viewModule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taghiveddemoapp.model.CryptoResponse
import com.example.taghiveddemoapp.model.GetCryptoResponse
import com.example.taghiveddemoapp.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainCryptoViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {

    var cryptoValue = MutableLiveData<CryptoResponse>()
    var cryptoDetailResult = MutableLiveData<GetCryptoResponse>()


    fun getCryptos() = viewModelScope.launch {
        var finalResult = repository.getCryptos()
        cryptoValue.postValue(finalResult.body())
    }


    fun getCryptoDetails(cryptoSymbol: String) =
        viewModelScope.launch {
            val cryptoDetailFinalResult = repository.getCryptoDetails(cryptoSymbol)
            cryptoDetailResult.postValue(cryptoDetailFinalResult.body())

        }
}