package com.example.petgallery.Upload

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petgallery.ui.theme.model.Pet
import com.example.petgallery.ui.theme.repository.FakePetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UploadScreenViewModel @Inject constructor( ): ViewModel() {

    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState = _uploadState.asStateFlow()

    private var isCancelled = false

    fun startUpload() {
        if (_uploadState.value == UploadState.Loading) return

        _uploadState.value = UploadState.Loading
        isCancelled = false

        viewModelScope.launch {
            delay(3000L) // simula operación larga (3 segundos)
            if (isCancelled) {
                _uploadState.value = UploadState.Cancelled
            } else {
                _uploadState.value = UploadState.Success
            }
        }
    }

    fun cancelUpload() {
        if (_uploadState.value == UploadState.Loading) {
            isCancelled = true
        }
    }

    fun forceError() {
        if (_uploadState.value == UploadState.Loading) {
            _uploadState.value = UploadState.Error
        }
    }

    fun resetUpload() {
        _uploadState.value = UploadState.Idle
        isCancelled = false
    }
}
sealed class UploadState {
    object Idle : UploadState()
    object Loading : UploadState()
    object Success : UploadState()
    object Cancelled : UploadState()
    object Error : UploadState()
}