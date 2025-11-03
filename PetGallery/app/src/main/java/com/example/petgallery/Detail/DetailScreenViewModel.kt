package com.example.petgallery.Detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.petgallery.Home.ListItemUiState
import com.example.petgallery.Home.ListUiState
import com.example.petgallery.destinations
import com.example.petgallery.ui.theme.model.Pet
import com.example.petgallery.ui.theme.repository.FakePetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val petRepository: FakePetRepository


): ViewModel() {
    private val _uiState: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState>
        get() =_uiState.asStateFlow()
    init {
        viewModelScope.launch {
            val route= savedStateHandle.toRoute<destinations.Detail>()
            val petId=route.id
            val pet =petRepository.readOne(petId)
            pet?.let{
                _uiState.value = pet.toDetailUiState()
            }
        }
    }
    fun Pet.toDetailUiState(): DetailUiState = DetailUiState(
        imageUrl = this.imageUrl,
        name = this.name,
        description = this.description,
    )
}
data class DetailUiState(
    val imageUrl:String="",
    val name:String="",
    val description:String="",
)
