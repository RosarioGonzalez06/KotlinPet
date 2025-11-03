package com.example.petgallery.ui.theme.repository

import com.example.petgallery.ui.theme.model.Pet

interface PetRepository {
    suspend fun getPets(): List<Pet>
    suspend fun simulateUpload(): Result<Unit>
    suspend fun readOne(id: String): Pet?
}