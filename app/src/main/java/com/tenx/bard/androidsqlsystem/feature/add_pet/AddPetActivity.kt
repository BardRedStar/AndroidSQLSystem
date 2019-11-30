package com.tenx.bard.androidsqlsystem.feature.add_pet

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tenx.bard.androidsqlsystem.MyApplication
import com.tenx.bard.androidsqlsystem.R
import com.tenx.bard.androidsqlsystem.api.models.Pet
import kotlinx.android.synthetic.main.activity_add_show.*
import kotlinx.android.synthetic.main.activity_add_show.toolbar

class AddPetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_show)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        title = "Add pet"
        tvTitle.text = "Pet name"
        etText.hint = "Type pet name here"

        val app = application as MyApplication

        Thread {
            val questions = app.database?.petQuestionDao()?.getAllQuestions()
            if (questions != null) {
                runOnUiThread {
                    rvList.adapter = PetProbabilityAdapter(questions, emptyList())
                }
            }
        }.start()

        rvList.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.actionAddObject) {
            val app = application as MyApplication

            val name = etText.text.toString()

            if (name.isEmpty() || name.isBlank()) {
                Toast.makeText(this, "Type the correct name!", Toast.LENGTH_LONG).show()
                return true
            }

            val pet = Pet(0, name)

            Thread {
                val id = app.database?.petQuestionDao()?.insertPet(pet)
                if (id != null) {
                    runOnUiThread {
                        saveProbabilitiesForPet(id)
                    }
                } else {
                    runOnUiThread {
                        title = "Error - id"
                    }
                }
            }.start()
        } else if (item?.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    private fun saveProbabilitiesForPet(petId: Long) {

        val probabilities = (rvList.adapter as PetProbabilityAdapter).probabilities
        if (probabilities.isEmpty()) {
            runOnUiThread {
                this.finish()
            }
            return
        }

        for (index in 0 until probabilities.size) {
            probabilities[index].petId = petId
        }
        val app = application as MyApplication
        Thread {
            app.database?.petQuestionDao()?.insertProbabilities(probabilities)
            runOnUiThread {
                this.finish()
            }
        }.start()
    }
}