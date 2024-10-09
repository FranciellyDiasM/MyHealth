package br.com.quatrodcum.myhealth.view.config.ingredient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.quatrodcum.myhealth.databinding.ItemSimpleCrudBinding
import br.com.quatrodcum.myhealth.model.domain.Ingredient

class IngredientAdapter :
    ListAdapter<Ingredient, IngredientAdapter.IngredientViewHolder>(
        IngredientDiffCallback()
    ) {

    private var _onItemClickListener: (Ingredient) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemSimpleCrudBinding.inflate(layoutInflater, parent, false)

        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.bind(ingredient)
    }

    fun setOnItemClickListener(action: (Ingredient) -> Unit) {
        _onItemClickListener = action
    }


    inner class IngredientViewHolder(
        private val binding: ItemSimpleCrudBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val clickedItem = currentList[adapterPosition]
                _onItemClickListener.invoke(clickedItem)
            }
        }

        fun bind(ingredient: Ingredient) {
            binding.txtName.text = ingredient.name
        }
    }

    fun submitItem(item: Ingredient) {
        val tempList = this.currentList.toMutableList()
        val index = tempList.indexOfFirst { it.id == item.id }

        if(index >= 0) {
            tempList[index] = item
        }

        this.submitList(tempList)
    }

    fun remove(item: Ingredient) {
        val tempList = this.currentList.toMutableList()
        val index = tempList.indexOfFirst { it.id == item.id }

        if(index >= 0) {
            tempList.removeAt(index)
        }

        this.submitList(tempList)
    }

    class IngredientDiffCallback : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(
            oldItem: Ingredient,
            newItem: Ingredient
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Ingredient,
            newItem: Ingredient
        ): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }
    }
}