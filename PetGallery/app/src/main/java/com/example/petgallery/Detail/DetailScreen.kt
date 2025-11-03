package com.example.petgallery.Detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailScreenViewModel= hiltViewModel(),
    onCancel: ()-> Unit,
){
    val uiState by viewModel.uiState.collectAsState()
    val imageModifier = Modifier.size(100.dp).padding(8.dp).clip(CircleShape)
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model= uiState.imageUrl,
            contentDescription ="",
            modifier= imageModifier,
            )
        Text(
            text = uiState.name,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = uiState.description,
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = onCancel
        ) {
            Text("return")
        }

    }
}
