package com.tenx.bard.androidsqlsystem.api

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tenx.bard.androidsqlsystem.api.models.Pet
import com.tenx.bard.androidsqlsystem.api.models.Probability
import com.tenx.bard.androidsqlsystem.api.models.Question

@Database(entities = arrayOf(Probability::class, Pet::class, Question::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petQuestionDao(): PetQuestionDao
}