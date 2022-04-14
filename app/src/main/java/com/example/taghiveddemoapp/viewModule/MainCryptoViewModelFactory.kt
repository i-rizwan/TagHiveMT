package com.example.taghiveddemoapp.viewModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taghiveddemoapp.network.ApiRepository

class MainCryptoViewModelFactory(private val repository: ApiRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainCryptoViewModel(repository) as T
    }
}