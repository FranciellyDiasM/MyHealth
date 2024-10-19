package br.com.quatrodcum.myhealth.view.config.unitofmeasurement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.quatrodcum.myhealth.databinding.ItemSimpleCrudBinding
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement

class UnitOfMeasurementAdapter :
    ListAdapter<UnitOfMeasurement, UnitOfMeasurementAdapter.UnitOfMeasurementViewHolder>(
        UnitOfMeasurementDiffCallback()
    ) {

    private var _onItemClickListener: (UnitOfMeasurement) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitOfMeasurementViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemSimpleCrudBinding.inflate(layoutInflater, parent, false)

        return UnitOfMeasurementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UnitOfMeasurementViewHolder, position: Int) {
        val unitOfMeasurementView = getItem(position)
        holder.bind(unitOfMeasurementView)
    }

    fun setOnItemClickListener(action: (UnitOfMeasurement) -> Unit) {
        _onItemClickListener = action
    }


    inner class UnitOfMeasurementViewHolder(
        private val binding: ItemSimpleCrudBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val clickedItem = currentList[adapterPosition]
                _onItemClickListener.invoke(clickedItem)
            }
        }

        fun bind(unitOfMeasurement: UnitOfMeasurement) {
            binding.txtName.text = unitOfMeasurement.name
        }
    }

    fun submitItem(item: UnitOfMeasurement) {
        val tempList = this.currentList.toMutableList()
        val index = tempList.indexOfFirst { it.id == item.id }

        if(index >= 0) {
            tempList[index] = item
        }

        this.submitList(tempList)
    }

    fun remove(item: UnitOfMeasurement) {
        val tempList = this.currentList.toMutableList()
        val index = tempList.indexOfFirst { it.id == item.id }

        if(index >= 0) {
            tempList.removeAt(index)
        }

        this.submitList(tempList)
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