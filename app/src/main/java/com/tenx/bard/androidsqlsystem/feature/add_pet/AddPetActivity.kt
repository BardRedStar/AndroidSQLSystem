package com.tenx.bard.androidsqlsystem.feature.add_pet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tenx.bard.androidsqlsystem.MyApplication
import com.tenx.bard.androidsqlsystem.R
import com.tenx.bard.androidsqlsystem.api.models.Pet
import com.tenx.bard.androidsqlsystem.feature.admin.AdminActivity
import com.tenx.bard.androidsqlsystem.util.editTextToTextView
import kotlinx.android.synthetic.main.activity_add_show.*
import kotlinx.android.synthetic.main.activity_add_show.toolbar
import kotlinx.android.synthetic.main.activity_main.*

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
                    Log.w("PETS", "adapter set!")
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
            val pet = Pet(0, name)

            Thread {
                val id = app.database?.petQuestionDao()?.insertPet(pet)
                Log.w("PETS", "insert pet: $id")
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

        }
        return true
    }

    private fun saveProbabilitiesForPet(petId: Long) {

        val probabilities = (rvList.adapter as PetProbabilityAdapter).probabilities
        if (probabilities.isEmpty()) {
            Log.w("PETS", "finish is empty")
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
            Log.w("PETS", "insert probabilities")
            app.database?.petQuestionDao()?.insertProbabilities(probabilities)
            runOnUiThread {
                this.finish()
                Log.w("PETS", "finish after probability adding")
            }
        }.start()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}