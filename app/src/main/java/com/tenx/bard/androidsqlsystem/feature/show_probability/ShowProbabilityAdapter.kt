package com.tenx.bard.androidsqlsystem.feature.show_probability
import com.tenx.bard.androidsqlsystem.api.models.Probability
import com.tenx.bard.androidsqlsystem.base.adapter.ObjectProbabilityAdapter
import com.tenx.bard.androidsqlsystem.util.editTextToTextView

class ShowProbabilityAdapter(val isPets: Boolean, items: List<ShowProbabilityItem>, probabilities: List<Probability>):
        ObjectProbabilityAdapter<ShowProbabilityItem>(items, probabilities) {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleTextView.text = objects[position].text
        val probability = probabilities.firstOrNull { (if (isPets) it.petId else it.questionId) == objects[position].id }

        holder.plusEditText.setText(probability?.plus.toString())
        holder.minusEditText.setText(probability?.minus.toString())
        editTextToTextView(holder.plusEditText)
        editTextToTextView(holder.minusEditText)

    }
}