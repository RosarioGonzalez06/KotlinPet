package com.example.petgallery

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.petgallery.Detail.DetailScreen
import com.example.petgallery.Detail.DetailScreenViewModel
import com.example.petgallery.Home.HomeScreen
import com.example.petgallery.Home.HomeScreenViewModel
import com.example.petgallery.Home.ListUiState
import com.example.petgallery.Upload.UploadScreen
import com.example.petgallery.ui.theme.model.Pet
import com.example.petgallery.ui.theme.repository.FakePetRepository

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun PetApp() {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Pet App",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            )
        },
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = destinations.Home) {
            val hostModifier = Modifier
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding)
            composable<destinations.Home> {
                HomeScreen(
                    modifier = hostModifier,
                    onNavigateToDetail = { id ->
                        navController.navigate(destinations.Detail(id))
                    },
                    onNavigateToUpload = { navController.navigate(destinations.Upload) },
                    navController = navController
                )
            }
            composable<destinations.Detail> {
                DetailScreen (
                    modifier = hostModifier,
                    onCancel = { navController.popBackStack() },
                )

            }

            composable<destinations.Upload> {
                UploadScreen(
                )
            }
        }
    }
}
