package com.tenx.bard.androidsqlsystem.feature.survey

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.tenx.bard.androidsqlsystem.R
import kotlinx.android.synthetic.main.layout_question_page.view.*

class QuestionFragment(val question: String, val questionId: Long, val isLast: Boolean) : Fragment() {

    val buttons: MutableList<Button> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_question_page, null)
        view.tvQuestion.text = question
        view.btnYes.setOnClickListener {
            (activity as SurveyActivity).setAnswer(questionId, 1.0)
            changeColor(it)
            swipeRight()
        }
        view.btnProbablyYes.setOnClickListener {
            (activity as SurveyActivity).setAnswer(questionId, 0.75)
            changeColor(it)
            swipeRight()
        }
        view.btnDontKnow.setOnClickListener {
            (activity as SurveyActivity).setAnswer(questionId, 0.5)
            changeColor(it)
            swipeRight()
        }
        view.btnProbablyNo.setOnClickListener {
            (activity as SurveyActivity).setAnswer(questionId, 0.25)
            changeColor(it)
            swipeRight()
        }
        view.btnNo.setOnClickListener {
            (activity as SurveyActivity).setAnswer(questionId, 0.0)
            changeColor(it)
            swipeRight()
        }
        if (isLast) {
            view.fabResult.show()
            view.fabResult.setOnClickListener {
                (activity as SurveyActivity).finishSurvey()
            }
        }
        buttons.add(view.btnYes)
        buttons.add(view.btnProbablyYes)
        buttons.add(view.btnDontKnow)
        buttons.add(view.btnProbablyNo)
        buttons.add(view.btnNo)
        return view
    }

    fun swipeRight() {
        (activity as SurveyActivity).showNext()
    }

    private fun changeColor(button: View) {
        buttons.forEach {
            it.background = null
        }
        button.background = ColorDrawable(context!!.resources.getColor(R.color.colorAnswerChosen))
    }
}
