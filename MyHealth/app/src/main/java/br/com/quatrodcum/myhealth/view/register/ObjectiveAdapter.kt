package br.com.quatrodcum.myhealth.view.register

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.quatrodcum.myhealth.databinding.SpinnerItemBinding
import br.com.quatrodcum.myhealth.model.domain.Objective

class ObjectiveAdapter (
    private val context: Context,
    private val objectives: List<Objective>
) : BaseAdapter() {

    override fun getCount(): Int {
        return objectives.size
    }

    override fun getItem(position: Int): Objective {
        return objectives[position]
    }

    override fun getItemId(position: Int): Long {
        return objectives[position].id?.toLong() ?: -1
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder

        if (convertView == null) {
            val binding = SpinnerItemBinding.inflate(LayoutInflater.from(context), parent, false)
            viewHolder = ViewHolder(binding)
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.bind(objectives[position])

        return viewHolder.binding.root
    }

    fun getSelectedObjective(position: Int): Objective {
        return getItem(position)
    }

    private class ViewHolder(val binding: SpinnerItemBinding) {

        init {
            binding.root.tag = this
        }

        fun bind(objective: Objective) {
            binding.textSpinnerItem.text = objective.description
        }

    }
}