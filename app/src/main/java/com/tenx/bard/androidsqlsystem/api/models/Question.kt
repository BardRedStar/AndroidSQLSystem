package com.tenx.bard.androidsqlsystem.api.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "text") val text: String
)