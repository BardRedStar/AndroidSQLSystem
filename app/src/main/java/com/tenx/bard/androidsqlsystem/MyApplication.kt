package com.tenx.bard.androidsqlsystem

import android.app.Application
import androidx.room.Room
import com.tenx.bard.androidsqlsystem.api.AppDatabase

class MyApplication: Application() {

    var database: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "petquestion"
        ).build()
    }
}