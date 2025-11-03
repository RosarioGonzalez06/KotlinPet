package com.example.petgallery.Upload

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun UploadScreen(viewModel: UploadScreenViewModel = hiltViewModel()) {
    val state by viewModel.uploadState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (state) {
                is UploadState.Idle -> {
                    Text("Listo para comenzar", fontWeight = FontWeight.Bold)
                }
                is UploadState.Loading -> {
                    CircularProgressIndicator()
                    Text("Subiendo imagen...", fontWeight = FontWeight.Bold)
                }
                is UploadState.Success -> {
                    Text("Carga completada con éxito", fontWeight = FontWeight.Bold)
                }
                is UploadState.Cancelled -> {
                    Text("Operación cancelada", fontWeight = FontWeight.Bold)
                }
                is UploadState.Error -> {
                    Text("Ocurrió un error en la subida", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (state) {
                    is UploadState.Idle -> {
                        Button(onClick = { viewModel.startUpload() }) {
                            Text("Iniciar")
                        }
                    }
                    is UploadState.Loading -> {
                        Button(onClick = { viewModel.cancelUpload() }) {
                            Text("Cancelar")
                        }
                        Button(onClick = { viewModel.forceError() }) {
                            Text("Forzar error")
                        }
                    }
                    is UploadState.Success,
                    is UploadState.Cancelled,
                    is UploadState.Error -> {
                        Button(onClick = { viewModel.resetUpload() }) {
                            Text("Reiniciar")
                        }
                    }
                }
            }
        }
    }
}