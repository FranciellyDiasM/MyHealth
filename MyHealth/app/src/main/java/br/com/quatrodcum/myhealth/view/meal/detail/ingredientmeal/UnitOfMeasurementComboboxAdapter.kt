package br.com.quatrodcum.myhealth.view.meal.detail.unitOfMeasurementmeal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.quatrodcum.myhealth.databinding.SpinnerItemBinding
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement

class UnitOfMeasurementComboboxAdapter(
    private val unitOfMeasurements: List<UnitOfMeasurement>
) : BaseAdapter() {

    override fun getCount(): Int {
        return unitOfMeasurements.size
    }

    override fun getItem(position: Int): UnitOfMeasurement {
        return unitOfMeasurements[position]
    }

    override fun getItemId(position: Int): Long {
        return unitOfMeasurements[position].id?.toLong() ?: -1
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(parent!!.context)
            val binding = SpinnerItemBinding.inflate(layoutInflater, parent, false)
            viewHolder = ViewHolder(binding)
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.bind(unitOfMeasurements[position])

        return viewHolder.binding.root
    }

    fun getSelectedUnitOfMeasurement(position: Int): UnitOfMeasurement {
        return getItem(position)
    }

    private class ViewHolder(val binding: SpinnerItemBinding) {

        init {
            binding.root.tag = this
        }

        fun bind(unitOfMeasurement: UnitOfMeasurement) {
            binding.textSpinnerItem.text = unitOfMeasurement.name
        }

    }
}