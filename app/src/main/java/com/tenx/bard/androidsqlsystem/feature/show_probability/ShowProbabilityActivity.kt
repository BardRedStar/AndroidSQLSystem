package com.tenx.bard.androidsqlsystem.feature.show_probability

import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tenx.bard.androidsqlsystem.MyApplication
import com.tenx.bard.androidsqlsystem.R
import com.tenx.bard.androidsqlsystem.api.models.Probability
import com.tenx.bard.androidsqlsystem.util.editTextToTextView
import kotlinx.android.synthetic.main.activity_add_show.*

class ShowProbabilityActivity: AppCompatActivity() {

    companion object {
        val TYPE_PET = "pet"
        val TYPE_QUESTION = "question"

        val KEY_ID = "id"
        val KEY_TYPE = "type"
    }

    var isPet = true
    var id: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_show)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val type = intent.getStringExtra(KEY_TYPE)
        isPet = type == TYPE_PET
        id = intent.getLongExtra(KEY_ID, -1)

        title = if (isPet) "Pet information" else "Question information"
        tvTitle.text = if (isPet) "Pet name" else "Question text"


        val app = application as MyApplication

        editTextToTextView(etText)
        Thread {
            val text = if (isPet)
                app.database?.petQuestionDao()?.getAllPets()?.firstOrNull { it.uid == id }?.name
            else
                app.database?.petQuestionDao()?.getAllQuestions()?.firstOrNull { it.uid == id }?.text
            if (text != null) {
                runOnUiThread {
                    etText.setText(text)
                }
            }
        }.start()

        rvList.layoutManager = LinearLayoutManager(this)

        Thread {
            val probabilities: List<Probability>
            val items: List<ShowProbabilityItem>
            if (isPet) {
                items = app.database?.petQuestionDao()?.getAllQuestions()?.map { ShowProbabilityItem(it.uid, it.text) } ?: emptyList()
                probabilities = app.database?.petQuestionDao()?.getProbabilitiesForPet(id) ?: emptyList()
            } else {
                items = app.database?.petQuestionDao()?.getAllPets()?.map { ShowProbabilityItem(it.uid, it.name) } ?: emptyList()
                probabilities = app.database?.petQuestionDao()?.getProbabilitiesForQuestion(id) ?: emptyList()
            }
            runOnUiThread {
                val adapter = ShowProbabilityAdapter(!isPet, items, probabilities)
                rvList.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }.start()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
