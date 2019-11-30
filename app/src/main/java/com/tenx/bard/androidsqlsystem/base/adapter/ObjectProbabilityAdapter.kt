package com.tenx.bard.androidsqlsystem.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tenx.bard.androidsqlsystem.R
import com.tenx.bard.androidsqlsystem.api.models.Probability

open class ObjectProbabilityAdapter<AdapterItem> (var objects: List<AdapterItem>, var probabilities: List<Probability>) :
        RecyclerView.Adapter<ObjectProbabilityAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleTextView: TextView
        val plusEditText: EditText
        val minusEditText: EditText

        init {
            titleTextView = view.findViewById(R.id.tvTitle)
            plusEditText = view.findViewById(R.id.etPlus)
            minusEditText = view.findViewById(R.id.etMinus)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.probability_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount() = objects.size
}