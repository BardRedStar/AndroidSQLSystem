package com.tenx.bard.androidsqlsystem.feature.add_pet

import com.tenx.bard.androidsqlsystem.api.models.Probability
import com.tenx.bard.androidsqlsystem.api.models.Question
import com.tenx.bard.androidsqlsystem.base.adapter.ObjectProbabilityAdapter

class PetProbabilityAdapter(questions: List<Question>, probabilities: List<Probability>) :
        ObjectProbabilityAdapter<Question>(questions, probabilities) {

    init {
        this.probabilities = questions.map { Probability(0, 0, it.uid, 0.0, 0.0)}
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleTextView.text = objects[position].text

        val probability = probabilities.firstOrNull { it.questionId == objects[position].uid }
        if (probability != null) {
            holder.plusEditText.setText(probability.plus.toString())
            holder.minusEditText.setText(probability.minus.toString())
        }
        holder.plusEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val index = probabilities.indexOfFirst{ it.questionId == objects[position].uid }
                val probabilitiesMutable = probabilities.toMutableList()
                val value = if (holder.plusEditText.text.toString().isEmpty()) 0.0 else holder.plusEditText.text.toString().toDouble()
                if (index != -1) {
                    probabilitiesMutable[index].plus = value
                } else {
                    probabilitiesMutable.add(Probability(0, 0, objects[position].uid, value, 0.0))
                }
                probabilities = probabilitiesMutable
            }
        }
        holder.minusEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val index = probabilities.indexOfFirst{ it.questionId == objects[position].uid }
                val probabilitiesMutable = probabilities.toMutableList()
                val value = if (holder.minusEditText.text.toString().isEmpty()) 0.0 else holder.minusEditText.text.toString().toDouble()
                if (index != -1) {
                    probabilitiesMutable[index].minus = value
                } else {
                    probabilitiesMutable.add(Probability(0, 0, objects[position].uid, 0.0, value))
                }
                probabilities = probabilitiesMutable
            }
        }
    }
}