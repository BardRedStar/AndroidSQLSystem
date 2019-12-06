package com.tenx.bard.androidsqlsystem.feature.survey

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tenx.bard.androidsqlsystem.api.models.Question


class PagerAdapter(fm: FragmentManager, val questions: List<Question>) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment =
        QuestionFragment(questions[position].text, questions[position].uid, position == questions.size - 1)


    // this counts total number of tabs
    override fun getCount(): Int {
        return questions.size
    }


}