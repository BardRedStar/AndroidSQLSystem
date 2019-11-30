package com.tenx.bard.androidsqlsystem.feature.add_question

import com.tenx.bard.androidsqlsystem.api.models.Pet
import com.tenx.bard.androidsqlsystem.api.models.Probability
import com.tenx.bard.androidsqlsystem.base.adapter.ObjectProbabilityAdapter

class QuestionProbabilityAdapter(pets: List<Pet>, probabilities: List<Probability>):
        ObjectProbabilityAdapter<Pet>(pets, probabilities) {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleTextView.text = objects[position].name

        val probability = probabilities.firstOrNull { it.petId == objects[position].uid }
        if (probability != null) {
            holder.plusEditText.setText(probability.plus.toString())
            holder.minusEditText.setText(probability.minus.toString())
        }
        holder.plusEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val index = probabilities.indexOfFirst{ it.petId == objects[position].uid }
                val probabilitiesMutable = probabilities.toMutableList()
                val value = if (holder.plusEditText.text.toString().isEmpty()) 0.0 else holder.plusEditText.text.toString().toDouble()
                if (index != -1) {
                    probabilitiesMutable[index].plus = value
                } else {
                    probabilitiesMutable.add(Probability(0, objects[position].uid, 0, value, 0.0))
                }
                probabilities = probabilitiesMutable
            }
        }
        holder.minusEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val index = probabilities.indexOfFirst{ it.petId == objects[position].uid }
                val probabilitiesMutable = probabilities.toMutableList()
                val value = if (holder.minusEditText.text.toString().isEmpty()) 0.0 else holder.minusEditText.text.toString().toDouble()
                if (index != -1) {
                    probabilitiesMutable[index].minus = value
                } else {
                    probabilitiesMutable.add(Probability(0, objects[position].uid, 0, 0.0, value))
                }
                probabilities = probabilitiesMutable
            }
        }
    }
}