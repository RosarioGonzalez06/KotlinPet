package com.example.petgallery.ui.theme.repository

import com.example.petgallery.ui.theme.model.Pet
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlin.random.Random

class FakePetRepository @Inject constructor(): PetRepository {
    private val pets = listOf(
        Pet("1", "Gato", "https://placekitten.com/400/300", "Un gato tierno"),
        Pet("2", "Perro", "https://placedog.net/500/300", "Un perro feliz"),
        Pet("3", "Pájaro", "https://picsum.photos/500/350", "Un pájaro libre")
    )

    override suspend fun getPets(): List<Pet> {
        delay(500)
        return pets
    }

    override suspend fun simulateUpload(): Result<Unit> {
        delay(2000)
        return if (Random.nextBoolean()) Result.success(Unit)
        else Result.failure(Exception("Error al subir"))
    }

     override suspend fun readOne(id: String): Pet? {
        val pet = pets.firstOrNull() {
                p -> p.id == id
        }
        return pet
    }

}