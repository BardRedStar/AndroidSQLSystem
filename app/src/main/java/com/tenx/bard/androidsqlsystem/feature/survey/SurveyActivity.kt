package com.tenx.bard.androidsqlsystem.feature.survey

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.tenx.bard.androidsqlsystem.MyApplication
import com.tenx.bard.androidsqlsystem.R
import com.tenx.bard.androidsqlsystem.ResultActivity
import com.tenx.bard.androidsqlsystem.api.PetQuestionDao
import com.tenx.bard.androidsqlsystem.api.models.Question
import com.tenx.bard.androidsqlsystem.feature.admin.AdminActivity
import kotlinx.android.synthetic.main.activity_survey.*
import kotlinx.android.synthetic.main.layout_question_page.view.*
import kotlin.math.max

class SurveyActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    var dao: PetQuestionDao? = null
    lateinit var questions: List<Question>
    var resultMap = mutableMapOf<Long, Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        setSupportActionBar(toolbar)
        dao = (application as MyApplication).database?.petQuestionDao()
        initUi()
    }

    fun setAnswer(questionId: Long, value: Double) {
        resultMap[questionId] = value
    }

    fun showNext() {
        if (vpQuestions.currentItem < questions.size - 1) {
            vpQuestions.currentItem = vpQuestions.currentItem + 1
        }
    }

    fun showPrevious() {
        if (vpQuestions.currentItem > 0) {
            vpQuestions.currentItem = vpQuestions.currentItem - 1
        }
    }

    fun setQuestionNumber(number: Int) {
        llQuestionLayout.tvQuestion.text = "Вопрос №${number + 1}"
    }

    fun initUi() {
        dao?.let {
            Thread {
                questions = it.getAllQuestions()
                runOnUiThread {
                    if (questions.isEmpty()) {
                        llQuestionLayout.visibility = View.GONE
                        tvNoData.visibility = View.VISIBLE
                    } else {
                        initButtons()
                    }
                    initViewPager(questions)
                    setQuestionNumber(vpQuestions.currentItem)
                }
            }.start()
        }
    }

    fun initButtons() {
        btnLeft.setOnClickListener { showPrevious() }
        btnRight.setOnClickListener { showNext() }
    }

    fun initViewPager(questions: List<Question>) {
        vpQuestions.adapter = PagerAdapter(supportFragmentManager, questions)
        vpQuestions.addOnPageChangeListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.actionEditMode) {
            val intent = Intent(this, AdminActivity::class.java)
            startActivityForResult(intent, 2)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_edit_menu, menu)
        return true
    }


    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        setQuestionNumber(position)
    }

    fun finishSurvey() {
        if (resultMap.keys.size < questions.size) {
            navigateToUnansweredQuestion()
            return
        }
        dao?.let { dao ->
            Thread {
                val pets = dao.getAllPets()
                val results = mutableMapOf<String, Double>()
                val p = 1.0 / pets.size
                pets.forEach { pet ->
                    val probabilities = dao.getProbabilitiesForPet(pet.uid)
                    probabilities.forEach { probability ->
                        val answer = resultMap[probability.questionId]!!
                        results[pet.name] = max(answer * probability.plus * p / (probability.plus * p + probability.minus * (1.0 - p)), results[pet.name] ?: 0.0)
                    }
                }
                val prediction = results.maxWith(Comparator<Map.Entry<String, Double>> { p0, p1 ->
                    p0.value.compareTo(p1.value)
                })

                runOnUiThread {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("result", prediction?.key ?: "placeholder")
                    startActivityForResult(intent, 1)
                }
            }.start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        reinitUi()
    }

    fun reinitUi() {
        questions = emptyList()
        resultMap.clear()
        vpQuestions.removeAllViews()
        initUi()
    }

    private fun navigateToUnansweredQuestion() {
        questions.forEachIndexed { index, it ->
            if (!resultMap.containsKey(it.uid)) {
                vpQuestions.currentItem = index
            }
        }
    }
}
