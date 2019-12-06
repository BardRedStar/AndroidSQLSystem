package com.tenx.bard.androidsqlsystem.feature.add_question

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tenx.bard.androidsqlsystem.MyApplication
import com.tenx.bard.androidsqlsystem.R
import com.tenx.bard.androidsqlsystem.api.models.Question
import kotlinx.android.synthetic.main.activity_add_show.*
import kotlinx.android.synthetic.main.activity_add_show.toolbar

class AddQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_show)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        title = "Add question"
        tvTitle.text = "Question text"
        etText.hint = "Type question text here"

        val app = application as MyApplication

        Thread {
            val pets = app.database?.petQuestionDao()?.getAllPets()
            if (pets != null) {
                runOnUiThread {
                    rvList.adapter = QuestionProbabilityAdapter(pets, emptyList())
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

            val text = etText.text.toString()
            etText.requestFocus()

            if (text.isEmpty() || text.isBlank()) {
                Toast.makeText(this, "Type the correct question text!", Toast.LENGTH_LONG).show()
                return true
            }
            val question = Question(0, text)

            Thread {
                val id = app.database?.petQuestionDao()?.insertQuestion(question)
                if (id != null) {
                    runOnUiThread {
                        saveProbabilitiesForQuestion(id)
                    }
                }
            }.start()

        } else if (item?.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    private fun saveProbabilitiesForQuestion(questionId: Long) {

        val probabilities = (rvList.adapter as QuestionProbabilityAdapter).probabilities
        if (probabilities.isEmpty()) {
            finish()
            return
        }

        for (index in 0 until probabilities.size) {
            probabilities[index].questionId = questionId
        }
        val app = application as MyApplication

        Thread {
            app.database?.petQuestionDao()?.insertProbabilities(probabilities)
            runOnUiThread {
                finish()
            }
        }.start()

    }
}