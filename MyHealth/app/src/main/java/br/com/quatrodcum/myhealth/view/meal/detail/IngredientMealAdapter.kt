package br.com.quatrodcum.myhealth.view.meal.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.quatrodcum.myhealth.databinding.ItemIgredientMealBinding
import br.com.quatrodcum.myhealth.model.domain.IngredientMeal

class IngredientMealAdapter :
    ListAdapter<IngredientMeal, IngredientMealAdapter.IngredientMealViewHolder>(
        IngredientMealDiffCallback()
    ) {

    private var _onItemClickListener: (IngredientMeal) -> Unit = {}
    var isEnabled: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientMealViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemIgredientMealBinding.inflate(layoutInflater, parent, false)

        return IngredientMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientMealViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun onItemClickListener(action: (IngredientMeal) -> Unit) {
        _onItemClickListener = action
    }

    fun submitItem(item:IngredientMeal) {
        val tempList = this.currentList.toMutableList()
        tempList.add(item)

        this.submitList(tempList)
    }

    fun remove(item:IngredientMeal) {
        val tempList = this.currentList.toMutableList()
        val index = tempList.indexOfFirst {
            it == item
        }

        if(index >= 0) {
            tempList.removeAt(index)
        }

        this.submitList(tempList)
    }

    inner class IngredientMealViewHolder(
        private val binding: ItemIgredientMealBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if(isEnabled) {
                    _onItemClickListener.invoke(currentList[adapterPosition])
                }
            }
        }

        fun bind(ingredientMeal: IngredientMeal) {
            binding.txtIngredient.text = ingredientMeal.ingredient.name
            binding.txtQuantity.text = ingredientMeal.quantity.toString()
            binding.txtUnitOfMeasurement.text = ingredientMeal.unitOfMeasurement.name
        }
    }

    class IngredientMealDiffCallback : DiffUtil.ItemCallback<IngredientMeal>() {
        override fun areItemsTheSame(oldItem: IngredientMeal, newItem: IngredientMeal): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: IngredientMeal, newItem: IngredientMeal): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
}