package com.lexneoapps.crypto100.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lexneoapps.crypto100.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
        private val coinRepository: CoinRepository
) : ViewModel(){


        val coins = coinRepository.getCoins().cachedIn(viewModelScope)



}