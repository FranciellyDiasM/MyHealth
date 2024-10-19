package br.com.quatrodcum.myhealth.view.meal.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.quatrodcum.myhealth.databinding.ItemMealBinding
import br.com.quatrodcum.myhealth.model.domain.Meal

class MealAdapter :
    ListAdapter<Meal, MealAdapter.MealViewHolder>(MealDiffCallback()) {

    private var _onItemClickListener: (Meal) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemMealBinding.inflate(layoutInflater, parent, false)

        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = getItem(position)
        holder.bind(meal)
    }

    fun setOnItemClickListener(action: (Meal) -> Unit) {
        _onItemClickListener = action
    }


    inner class MealViewHolder(
        private val binding: ItemMealBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val clickedItem = currentList[adapterPosition]
                _onItemClickListener.invoke(clickedItem)
            }
        }

        fun bind(meal: Meal) {
            binding.txtTitle.text = meal.name
            binding.txtDescription.text = meal.description
        }
    }

    fun submitItem(item: Meal) {
        val tempList = this.currentList.toMutableList()
        val index = tempList.indexOfFirst { it.id == item.id }

        if (index >= 0) {
            tempList[index] = item
        }

        this.submitList(tempList)
    }

    fun remove(item: Meal) {
        val tempList = this.currentList.toMutableList()
        val index = tempList.indexOfFirst { it.id == item.id }

        if (index >= 0) {
            tempList.removeAt(index)
        }

        this.submitList(tempList)
    }

    class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(
            oldItem: Meal,
            newItem: Meal
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Meal,
            newItem: Meal
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}