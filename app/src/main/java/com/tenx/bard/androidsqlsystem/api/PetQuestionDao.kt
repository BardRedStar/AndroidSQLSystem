package com.tenx.bard.androidsqlsystem.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tenx.bard.androidsqlsystem.api.models.Pet
import com.tenx.bard.androidsqlsystem.api.models.Probability
import com.tenx.bard.androidsqlsystem.api.models.Question

@Dao
interface PetQuestionDao {
    @Query("SELECT * FROM pets")
    fun getAllPets(): List<Pet>

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): List<Question>

    @Query("SELECT * FROM probabilities WHERE question_id=:questionId")
    fun getProbabilitiesForQuestion(questionId: Long): List<Probability>

    @Query("SELECT * FROM probabilities WHERE pet_id=:petId")
    fun getProbabilitiesForPet(petId: Long): List<Probability>

    @Query("DELETE FROM questions WHERE uid=:id")
    fun deleteQuestion(id: Long)

    @Query("DELETE FROM pets WHERE uid=:id")
    fun deletePet(id: Long)

    @Insert
    fun insertPet(pet: Pet): Long

    @Insert
    fun insertQuestion(question: Question): Long

    @Insert
    fun insertProbabilities(probabilities: List<Probability>)
}