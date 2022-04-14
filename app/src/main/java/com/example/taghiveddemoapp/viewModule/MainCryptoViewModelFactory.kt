package com.example.taghiveddemoapp.viewModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taghiveddemoapp.network.ApiRepository
import com.example.taghiveddemoapp.network.NetworkHelper

class MainCryptoViewModelFactory(
    private val repository: ApiRepository,
    private val networkHelper: NetworkHelper
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainCryptoViewModel(repository, networkHelper) as T
    }
}