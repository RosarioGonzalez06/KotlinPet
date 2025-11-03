package com.example.petgallery.Home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petgallery.ui.theme.model.Pet
import com.example.petgallery.ui.theme.repository.FakePetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle, public val repository: FakePetRepository): ViewModel(){
    private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Initial)
    val uiState: StateFlow<ListUiState>
        get()= _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            _uiState.value= ListUiState.Loading
            val allPets=repository.getPets()
            val successResponse = ListUiState.Success(allPets.asListUiState())
            _uiState.value=successResponse
            loadPet()
        }
    }
    fun loadPet() {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            val allPets = repository.getPets()
            _uiState.value = ListUiState.Success(allPets.asListUiState())
        }
    }

}


fun Pet.asListItemUiState(): ListItemUiState{
    return ListItemUiState(
        id=this.id,
        name=this.name,
        imageUrl=this.imageUrl
    )
}


fun List<Pet>.asListUiState(): List<ListItemUiState> = this.map(Pet::asListItemUiState)




data class ListItemUiState(
    val id: String,
    val name: String,
    val imageUrl: String,
)
sealed class ListUiState {
    object Initial : ListUiState()
    object Loading : ListUiState()
    data class Success(val pets: List<ListItemUiState>) : ListUiState()
}
