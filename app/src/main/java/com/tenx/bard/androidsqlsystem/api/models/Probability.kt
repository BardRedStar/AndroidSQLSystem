package com.tenx.bard.androidsqlsystem.api.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "probabilities",
        foreignKeys = [
            ForeignKey(entity = Pet::class, parentColumns = ["uid"],childColumns = ["pet_id"], onDelete = CASCADE),
            ForeignKey(entity = Question::class, parentColumns = ["uid"], childColumns = ["question_id"], onDelete = CASCADE)
        ]
)
data class Probability (
    @PrimaryKey(autoGenerate = true) var uid: Long,
    @ColumnInfo(name = "pet_id") var petId: Long,
    @ColumnInfo(name = "question_id") var questionId: Long,
    @ColumnInfo(name = "plus") var plus: Double = 0.0,
    @ColumnInfo(name = "minus") var minus: Double = 0.0
)