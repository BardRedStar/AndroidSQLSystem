package com.tenx.bard.androidsqlsystem.feature.admin

import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tenx.bard.androidsqlsystem.R
import com.tenx.bard.androidsqlsystem.feature.admin.models.AdminItem

class AdminItemAdapter (var objects: List<AdminItem>) :
        RecyclerView.Adapter<AdminItemAdapter.MyViewHolder>() {

    var onItemLongClicked: ((Int) -> Unit)? = null
    var onItemClicked: ((Int) -> Unit)? = null


    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val view = from(parent.context)
                .inflate(R.layout.admin_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val view = holder.view
        view.setOnLongClickListener{
            onItemLongClicked?.invoke(position)
            true
        }
        view.setOnClickListener{
            onItemClicked?.invoke(position)
        }
        val textView = view.findViewById<TextView>(R.id.textView)
        textView.text = objects[position].text
    }

    override fun getItemCount() = objects.size
}