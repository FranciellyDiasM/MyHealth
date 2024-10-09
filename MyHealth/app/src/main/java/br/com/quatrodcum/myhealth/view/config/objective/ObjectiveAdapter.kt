package br.com.quatrodcum.myhealth.view.config.objective

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.quatrodcum.myhealth.databinding.ItemSimpleCrudBinding
import br.com.quatrodcum.myhealth.model.domain.Objective

class ObjectiveAdapter :
    ListAdapter<Objective, ObjectiveAdapter.ObjectiveViewHolder>(
        ObjectiveDiffCallback()
    ) {

    private var _onItemClickListener: (Objective) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectiveViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemSimpleCrudBinding.inflate(layoutInflater, parent, false)

        return ObjectiveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ObjectiveViewHolder, position: Int) {
        val objective = getItem(position)
        holder.bind(objective)
    }

    fun setOnItemClickListener(action: (Objective) -> Unit) {
        _onItemClickListener = action
    }


    inner class ObjectiveViewHolder(
        private val binding: ItemSimpleCrudBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val clickedItem = currentList[adapterPosition]
                _onItemClickListener.invoke(clickedItem)
            }
        }

        fun bind(objective: Objective) {
            binding.txtName.text = objective.description
        }
    }

    fun submitItem(item: Objective) {
        val tempList = this.currentList.toMutableList()
        val index = tempList.indexOfFirst { it.id == item.id }

        if(index >= 0) {
            tempList[index] = item
        }

        this.submitList(tempList)
    }

    fun remove(item: Objective) {
        val tempList = this.currentList.toMutableList()
        val index = tempList.indexOfFirst { it.id == item.id }

        if(index >= 0) {
            tempList.removeAt(index)
        }

        this.submitList(tempList)
    }

    class ObjectiveDiffCallback : DiffUtil.ItemCallback<Objective>() {
        override fun areItemsTheSame(
            oldItem: Objective,
            newItem: Objective
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Objective,
            newItem: Objective
        ): Boolean {
            return oldItem.id == newItem.id && oldItem.description == newItem.description
        }
    }
}