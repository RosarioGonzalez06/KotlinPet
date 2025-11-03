package com.example.petgallery.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.petgallery.Detail.DetailScreenViewModel
import com.example.petgallery.ui.theme.model.Pet

@Composable
fun HomeScreenItem(
    modifier: Modifier = Modifier,
    name:String,
    imageUrl:String,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToUpload: () -> Unit,
    petId: String,
) {

    val imageModifier = Modifier.size(100.dp).padding(8.dp).clip(CircleShape)
    Card(
        modifier = modifier.fillMaxWidth().clickable(
            enabled = true,
            onClick = { onNavigateToDetail(petId)
            })
    ) {
        Row {
            AsyncImage(
                model= imageUrl,
                contentDescription ="",
                modifier= imageModifier,
            )
        }
        Text(text = name,
            style = MaterialTheme.typography.headlineSmall)
    }
}


@Composable
fun HomeScreen(modifier: Modifier, onNavigateToDetail: (String)-> Unit, viewModel: HomeScreenViewModel=hiltViewModel(),onNavigateToUpload: ()-> Unit,navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsState()
    val currentBackStackEntry = navController.currentBackStackEntry
    val savedStateHandle = currentBackStackEntry?.savedStateHandle
    when(uiState){
        is ListUiState.Initial->{
            TODO()
        }
        is ListUiState.Loading-> {
            Column(modifier=modifier.fillMaxSize(),
                verticalArrangement= Arrangement.Center,
                horizontalAlignment= Alignment.CenterHorizontally){
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )}
        }
        is ListUiState.Success -> {
            Column(modifier = modifier.fillMaxSize()) {
                Button(
                    onClick = onNavigateToUpload,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Ir a Upload")
                }
                LazyColumn(modifier = modifier.fillMaxSize()) {
                    items(items=(uiState as ListUiState.Success).pets, key= {
                            item ->
                        item.id
                    }) {
                        HomeScreenItem(
                            name = it.name,
                            imageUrl = it.imageUrl,
                            petId = it.id,
                            onNavigateToDetail = onNavigateToDetail,
                            onNavigateToUpload = onNavigateToUpload,
                        )
                    }
                }
            }
        }
    }
}
