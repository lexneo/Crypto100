package com.lexneoapps.crypto100.ui.home


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexneoapps.crypto100.data.remote.model.Data
import com.lexneoapps.crypto100.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _data: MutableLiveData<List<Data>> = MutableLiveData()
    val data: LiveData<List<Data>>
        get() = _data

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _homeStatus = MutableLiveData<HomeStatus>()
    val homeStatus: LiveData<HomeStatus>
        get() = _homeStatus

    private var currentPage = 1

    init {
        getData()
    }

    fun restartData() {
        currentPage = 1
        _data.value = null
        getData()
        _homeStatus.value = HomeStatus.REFRESHED
    }


    fun getData() {
        if (currentPage > 100) {
            _homeStatus.value = HomeStatus.END
            return
        }
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            Log.d(TAG, "isLoadingValue = ${_isLoading.value}")
            try {
                _isLoading.value = true
                val fetchedData = coinRepository.getData(currentPage).data
                currentPage += 10
                val currentData = _data.value ?: emptyList()
                _data.value = currentData + fetchedData
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e(TAG, "Exception $e")
                _homeStatus.value = HomeStatus.ERROR
            } finally {
                _isLoading.value = false
            }
        }
    }




}

enum class HomeStatus{
    REFRESHED,
    END,
    ERROR

}