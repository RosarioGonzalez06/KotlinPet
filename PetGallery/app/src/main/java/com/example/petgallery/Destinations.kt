package com.example.petgallery

import kotlinx.serialization.Serializable


@Serializable
sealed class destinations (val route: String) {
    @Serializable
    object Home: destinations("HomeScreen")
    @Serializable
    object Upload: destinations("UploadScreen")
    @Serializable
    data class Detail (val id:String): destinations("DetailScreen/$id")
}
