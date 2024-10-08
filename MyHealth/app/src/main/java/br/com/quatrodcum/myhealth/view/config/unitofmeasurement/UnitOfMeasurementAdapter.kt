package br.com.quatrodcum.myhealth.view.config.unitofmeasurement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.quatrodcum.myhealth.databinding.ItemUnitOfMeasurementBinding
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement

class UnitOfMeasurementAdapter :
    ListAdapter<UnitOfMeasurement, UnitOfMeasurementAdapter.UnitOfMeasurementViewHolder>(
        UnitOfMeasurementDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitOfMeasurementViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemUnitOfMeasurementBinding.inflate(layoutInflater, parent, false)

        return UnitOfMeasurementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UnitOfMeasurementViewHolder, position: Int) {
        val unitOfMeasurementView = getItem(position)
        holder.bind(unitOfMeasurementView)
    }


    inner class UnitOfMeasurementViewHolder(
        private val binding: ItemUnitOfMeasurementBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(unitOfMeasurement: UnitOfMeasurement) {
            binding.txtName.text = unitOfMeasurement.name
        }
    }

    class UnitOfMeasurementDiffCallback : DiffUtil.ItemCallback<UnitOfMeasurement>() {
        override fun areItemsTheSame(
            oldItem: UnitOfMeasurement,
            newItem: UnitOfMeasurement
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UnitOfMeasurement,
            newItem: UnitOfMeasurement
        ): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }
    }
}